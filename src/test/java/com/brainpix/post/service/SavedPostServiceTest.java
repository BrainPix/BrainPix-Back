package com.brainpix.post.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.SavedPostSimpleResponse;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.PostRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.profile.repository.UserRepository;
import com.brainpix.user.entity.Individual;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SavedPostServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private SavedPostRepository savedPostRepository;

	@Autowired
	private SavedPostService savedPostService;
	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("테마별 저장 게시물 - 개수와 순서에 따른 조회 테스트")
	void testFindSavedPostsByTheme() {
		// Given
		Individual user = Individual.builder()
			.identifier("user1")
			.password("password1")
			.name("Test User")
			.birthday(LocalDateTime.now().minusYears(20))
			.email("user1@example.com")
			.profileImage("profile.png")
			.profile(null)
			.build();
		userRepository.save(user);

		// RequestTask 저장
		for (int i = 0; i < 3; i++) {
			RequestTask requestTask = RequestTask.builder()
				.writer(user)
				.title("Request Task " + i)
				.content("Content for request task " + i)
				.category("Task")
				.viewCount(0L)
				.imageList(Collections.singletonList("image_task" + i + ".png"))
				.attachmentFileList(Collections.singletonList("attachment_task" + i + ".pdf"))
				.deadline(LocalDateTime.now().plusDays(10))
				.build();
			postRepository.save(requestTask);
			savedPostRepository.save(new SavedPost(user, requestTask));
		}

		// IdeaMarket 저장
		for (int i = 0; i < 4; i++) {
			Price price = Price.builder()
				.totalQuantity(100L)
				.occupiedQuantity(10L)
				.price(1000L * (i + 1)) // 고유한 가격
				.paymentDuration(PaymentDuration.MONTHLY)
				.build();
			entityManager.persist(price); // 각 IdeaMarket에 고유한 Price 저장

			IdeaMarket ideaMarket = IdeaMarket.builder()
				.writer(user)
				.title("Idea Market " + i)
				.content("Content for idea market " + i)
				.category("Market")
				.viewCount(0L)
				.imageList(Collections.singletonList("image_market" + i + ".png"))
				.attachmentFileList(Collections.singletonList("attachment_market" + i + ".pdf"))
				.price(price)
				.build();
			postRepository.save(ideaMarket);
			savedPostRepository.save(new SavedPost(user, ideaMarket));
		}

		// CollaborationHub 저장 (없음)
		// 데이터 생성 생략

		// When
		List<SavedPostSimpleResponse> requestTasks = savedPostService.findSavedRequestTasks(user.getId());
		List<SavedPostSimpleResponse> ideaMarkets = savedPostService.findSavedIdeaMarkets(user.getId());
		List<SavedPostSimpleResponse> collaborationHubs = savedPostService.findSavedCollaborationHubs(user.getId());

		// Then
		assertThat(requestTasks).hasSize(3);
		assertThat(ideaMarkets).hasSize(4);
		assertThat(collaborationHubs).isEmpty();

		// 검증
		assertThat(requestTasks.get(0).thumbnail()).isEqualTo("image_task0.png");
		assertThat(ideaMarkets.get(0).thumbnail()).isEqualTo("image_market0.png");

		// 데이터 순서 확인
		for (int i = 0; i < requestTasks.size(); i++) {
			assertThat(requestTasks.get(i).thumbnail()).isEqualTo("image_task" + i + ".png");
		}

		for (int i = 0; i < ideaMarkets.size(); i++) {
			assertThat(ideaMarkets.get(i).thumbnail()).isEqualTo("image_market" + i + ".png");
		}

		// 정렬 확인 (가장 최근 저장된 데이터가 먼저 반환되었는지 검증)
		assertThat(requestTasks.get(0).thumbnail()).isEqualTo("image_task0.png");
		assertThat(ideaMarkets.get(0).thumbnail()).isEqualTo("image_market0.png");
	}

}
