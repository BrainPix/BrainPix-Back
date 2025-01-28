package com.brainpix.post.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.PriceRepository;
import com.brainpix.post.dto.mypostdto.MyRequestTaskPostDetailDto;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.entity.request_task.RequestTaskType;
import com.brainpix.post.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.repository.RequestTaskRecruitmentRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

@SpringBootTest
@Transactional
public class MyRequestTaskPostManagementServiceTest {

	@Autowired
	private MyRequestTaskPostManagementService service;

	@Autowired
	private RequestTaskRepository requestTaskRepository;

	@Autowired
	private RequestTaskRecruitmentRepository recruitmentRepository;

	@Autowired
	private RequestTaskPurchasingRepository purchasingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PriceRepository priceRepository;

	@Test
	public void testGetRequestTaskDetail() {
		// 1. 데이터 준비
		User writer = createUser("writer", "Writer Name");
		User designer1 = createUser("designer1", "Designer User 1");
		User designer2 = createUser("designer2", "Designer User 2");
		User designer3 = createUser("designer3", "Designer User 3");
		User developer1 = createUser("developer1", "Developer User 1");
		User developer2 = createUser("developer2", "Developer User 2");
		User developer3 = createUser("developer3", "Developer User 3");

		// 2. 요청 과제 생성
		RequestTask task = createRequestTask(writer, "Web Service Proposal", "디자인 및 개발 요청");

		// 3. 모집 단위 생성 (디자이너, 개발자)
		RequestTaskRecruitment designerRecruitment = createRecruitment(task, "디자이너", 4L);
		RequestTaskRecruitment developerRecruitment = createRecruitment(task, "개발자", 2L);

		// 4. 지원자 생성
		RequestTaskPurchasing purchasing1 = createPurchasing(designerRecruitment, designer1, null); // 대기 중
		RequestTaskPurchasing purchasing2 = createPurchasing(designerRecruitment, designer2, null); // 대기 중
		RequestTaskPurchasing purchasing3 = createPurchasing(designerRecruitment, designer3, null); // 대기 중
		RequestTaskPurchasing purchasing4 = createPurchasing(developerRecruitment, developer1, null); // 대기 중
		RequestTaskPurchasing purchasing5 = createPurchasing(developerRecruitment, developer2, null); // 대기 중
		RequestTaskPurchasing purchasing6 = createPurchasing(developerRecruitment, developer3, null); // 대기 중

		// 5. 지원자 수락 처리
		service.acceptPurchasing(writer.getId(), purchasing4.getId()); // developer1 수락
		service.acceptPurchasing(writer.getId(), purchasing5.getId()); // developer2 수락

		// 6. API 호출
		MyRequestTaskPostDetailDto detailDto = service.getRequestTaskDetail(writer.getId(), task.getId());

		// 7. 검증
		assertThat(detailDto.getTitle()).isEqualTo("Web Service Proposal");
		assertThat(detailDto.getSupportStatus()).hasSize(4); // 대기 중: designer1, developer3
		assertThat(detailDto.getCurrentMembers()).hasSize(2); // 수락: designer2, designer3, developer1, developer2

		// 지원 현황 검증
		detailDto.getSupportStatus().forEach(support -> {
			if (support.getUserId().equals("designer1")) {
				assertThat(support.getRole()).isEqualTo("디자이너");
				assertThat(support.getCurrentSlashTotal()).isEqualTo("0 / 4");
			} else if (support.getUserId().equals("developer3")) {
				assertThat(support.getRole()).isEqualTo("개발자");
				assertThat(support.getCurrentSlashTotal()).isEqualTo("2 / 2");
			}
		});

		// 현재 인원 검증
		detailDto.getCurrentMembers().forEach(member -> {
			if (member.getRole().equals("디자이너")) {
				assertThat(member.getMemberCount()).isEqualTo(2); // designer2, designer3
			} else if (member.getRole().equals("개발자")) {
				assertThat(member.getMemberCount()).isEqualTo(2); // developer1, developer2
			}
		});

		// 출력 (Optional)
		System.out.println("지원 현황:");
		detailDto.getSupportStatus().forEach(support -> {
			System.out.println("아이디: " + support.getUserId() + ", 역할: " + support.getRole() + ", 인원: "
				+ support.getCurrentSlashTotal());
		});

		System.out.println("\n현재 인원:");
		detailDto.getCurrentMembers().forEach(member -> {
			System.out.println(
				"아이디: " + member.getUserId() + ", 역할: " + member.getRole() + ", 현재 인원수: " + member.getMemberCount());
		});
	}

	private User createUser(String identifier, String name) {
		User user = new Individual(identifier, "password", name, "nickname", LocalDate.now(), "email", null, null);
		return userRepository.save(user);
	}

	private RequestTask createRequestTask(User writer, String title, String content) {
		RequestTask task = RequestTask.builder()
			.writer(writer)
			.title(title)
			.content(content)
			.deadline(LocalDateTime.now().plusDays(21))
			.requestTaskType(RequestTaskType.OPEN_IDEA)
			.postAuth(PostAuth.ALL)
			.build();
		return requestTaskRepository.save(task);
	}

	private RequestTaskRecruitment createRecruitment(RequestTask task, String domain, Long totalQuantity) {
		Price price = priceRepository.save(
			Price.builder()
				.totalQuantity(totalQuantity)
				.occupiedQuantity(0L)
				.price(1000L)
				.paymentDuration(PaymentDuration.ONCE)
				.build()
		);
		RequestTaskRecruitment recruitment = RequestTaskRecruitment.builder()
			.requestTask(task)
			.domain(domain)
			.price(price)
			.build();
		return recruitmentRepository.save(recruitment);
	}

	private RequestTaskPurchasing createPurchasing(RequestTaskRecruitment recruitment, User buyer, Boolean accepted) {
		return purchasingRepository.save(
			RequestTaskPurchasing.builder()
				.requestTaskRecruitment(recruitment)
				.buyer(buyer)
				.price(1000L)
				.paymentDuration(PaymentDuration.ONCE)
				.accepted(accepted)
				.build()
		);
	}

}
