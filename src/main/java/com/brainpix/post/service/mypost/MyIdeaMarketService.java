package com.brainpix.post.service.mypost;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.post.dto.PostIdeaMarketResponse;
import com.brainpix.post.dto.mypostdto.MyIdeaMarketPostDetailResponse;
import com.brainpix.post.dto.mypostdto.PurchaseInfoResponse;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyIdeaMarketService {

	private final IdeaMarketRepository ideaMarketRepository;
	private final SavedPostRepository savedPostRepository;
	private final UserRepository userRepository;
	private final IdeaMarketPurchasingRepository ideaMarketPurchasingRepository;

	public Page<PostIdeaMarketResponse> findIdeaMarketPosts(long userId, Pageable pageable) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return ideaMarketRepository.findByWriter(writer, pageable)
			.map(ideaMarket -> {
				Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());
				boolean isSavedPost = savedPostRepository.existsByUserIdAndPostId(userId, ideaMarket.getId());
				return PostIdeaMarketResponse.from(ideaMarket, saveCount, isSavedPost);
			});
	}

	public MyIdeaMarketPostDetailResponse getMyIdeaMarketPostDetail(Long userId, Long postId) {
		IdeaMarket ideaMarket = ideaMarketRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		ideaMarket.validateWriter(userId);

		List<PurchaseInfoResponse> purchaseHistory = ideaMarketPurchasingRepository.findByIdeaMarket(ideaMarket)
			.stream()
			.map(purchase -> new PurchaseInfoResponse(
				purchase.getBuyer().getIdentifier(),
				purchase.getBuyer().getId(),
				purchase.getPayment(),
				purchase.getPrice()
			))
			.collect(Collectors.toList());

		return MyIdeaMarketPostDetailResponse.from(ideaMarket, purchaseHistory);

	}
}
