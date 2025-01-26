package com.brainpix.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.post.converter.MyIdeaMarketPostConverter;
import com.brainpix.post.dto.IdeaMarketPurchaseInfo;
import com.brainpix.post.dto.MyIdeaMarketPostDetailDto;
import com.brainpix.post.dto.MyIdeaMarketPostDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyIdeaMarketPostManagementService {

	private final IdeaMarketRepository ideaMarketRepository;
	private final IdeaMarketPurchasingRepository purchasingRepository;
	private final SavedPostRepository savedPostRepository;
	private final UserRepository userRepository;

	private final MyIdeaMarketPostConverter converter;

	/**
	 * [목록] 내가 작성한 아이디어마켓 게시글들
	 */
	@Transactional(readOnly = true)
	public List<MyIdeaMarketPostDto> getMyIdeaMarketPosts(Long userId) {
		// 1) User 조회
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

		// 2) 내가 작성한 IdeaMarket 목록
		List<IdeaMarket> myMarkets = ideaMarketRepository.findByWriter(currentUser);

		// 3) SavedPost count & DTO 변환
		return myMarkets.stream()
			.map(ideaMarket -> {
				long savedCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return converter.toMyIdeaMarketPostDto(ideaMarket, savedCount);
			})
			.collect(Collectors.toList());
	}

	/**
	 * [상세] 특정 아이디어마켓 게시글 + 구매자 정보
	 */
	@Transactional(readOnly = true)
	public MyIdeaMarketPostDetailDto getIdeaMarketDetail(Long userId, Long postId) {
		// 1) 현재 유저
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

		// 2) 게시글 찾기
		IdeaMarket ideaMarket = ideaMarketRepository.findById(postId)
			.orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

		// (작성자 권한 체크하려면)
		if (!ideaMarket.getWriter().equals(currentUser)) {
			throw new RuntimeException("본인이 작성한 게시글이 아닙니다.");
		}

		// 3) 구매 정보 리스트
		List<IdeaMarketPurchasing> purchasingList = purchasingRepository.findByIdeaMarket(ideaMarket);
		List<IdeaMarketPurchaseInfo> purchaseInfos = purchasingList.stream()
			.map(converter::toPurchaseInfo)
			.collect(Collectors.toList());

		// 4) DTO 변환
		return converter.toDetailDto(ideaMarket, purchaseInfos);
	}
}
