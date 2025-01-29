package com.brainpix.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.post.converter.MyIdeaMarketPostConverter;
import com.brainpix.post.dto.mypostdto.IdeaMarketPurchaseInfo;
import com.brainpix.post.dto.mypostdto.MyIdeaMarketPostDetailDto;
import com.brainpix.post.dto.mypostdto.MyIdeaMarketPostDto;
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
	public Page<MyIdeaMarketPostDto> getMyIdeaMarketPosts(Long userId, Pageable pageable) {
		// 1) User 조회
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		Pageable sortedPageable = PageRequest.of(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			Sort.by(Sort.Direction.DESC, "createdAt") // 최신순 정렬
		);

		// 2) 내가 작성한 IdeaMarket 목록 페이징
		return ideaMarketRepository.findByWriter(currentUser, pageable)
			.map(ideaMarket -> {
				long savedCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return converter.toMyIdeaMarketPostDto(ideaMarket, savedCount);
			});
	}

	/**
	 * [상세] 특정 아이디어마켓 게시글 + 구매자 정보
	 */
	@Transactional(readOnly = true)
	public MyIdeaMarketPostDetailDto getIdeaMarketDetail(Long userId, Long postId) {
		// 1) 현재 유저
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 2) 게시글 찾기
		IdeaMarket ideaMarket = ideaMarketRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.IDEA_MARKET_NOT_FOUND));

		// (작성자 권한 체크하려면)
		if (!ideaMarket.getWriter().equals(currentUser)) {
			throw new BrainPixException(PostErrorCode.NOT_POST_OWNER);
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
