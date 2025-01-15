package com.brainpix.post.service;

import com.brainpix.post.dto.ideamarket.IdeaMarketDetailResponse;
import com.brainpix.post.dto.ideamarket.IdeaMarketPreviewResponse;
import com.brainpix.post.dto.ideamarket.IdeaMarketPurchasingResponse;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketPurchasingRepository;
import com.brainpix.post.repository.IdeaMarketRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IdeaMarketService {

    private final IdeaMarketRepository ideaMarketRepository;
    private final IdeaMarketPurchasingRepository ideaMarketPurchasingRepository;

    /**
     * 본인 게시물 - 아이디어 마켓 미리보기
     * @param userId 사용자 ID
     * @return 아이디어 마켓 게시물 리스트
     */
    @Transactional(readOnly = true)
    public List<IdeaMarketPreviewResponse> getMyIdeaMarketPosts(Long userId) {
        return ideaMarketRepository.findByWriterId(userId).stream()
            .map(ideaMarket -> IdeaMarketPreviewResponse.builder()
                .id(ideaMarket.getId())
                .nickname(ideaMarket.getWriter().getName())
                .title(ideaMarket.getTitle())
                .profileImage(ideaMarket.getWriter().getProfileImage())
                .price(ideaMarket.getPrice().getPrice())
                .ideaMarketAuth(ideaMarket.getIdeaMarketAuth()) // 접근 권한 추가
                .imageUrl(ideaMarket.getImageList().isEmpty() ? null : ideaMarket.getImageList().get(0))
                .build())
            .collect(Collectors.toList());
    }

    /**
     * 본인 게시물 - 아이디어 마켓 상세보기
     * @param postId 게시물 ID
     * @return 아이디어 마켓 게시물 상세 정보
     */
    @Transactional(readOnly = true)
    public IdeaMarketDetailResponse getIdeaMarketDetail(Long postId) {
        IdeaMarket ideaMarket = ideaMarketRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 구매자 정보 조회
        List<IdeaMarketPurchasingResponse> purchasers = ideaMarketPurchasingRepository.findByIdeaMarketId(postId).stream()
            .map(purchase -> IdeaMarketPurchasingResponse.builder()
                .buyerName(purchase.getBuyer().getName())
                .paymentDuration(purchase.getPaymentDuration().name())
                .price(purchase.getPrice())
                .build())
            .collect(Collectors.toList());

        return IdeaMarketDetailResponse.builder()
            .id(ideaMarket.getId())
            .title(ideaMarket.getTitle())
            .price(ideaMarket.getPrice().getPrice())
            .imageUrl(ideaMarket.getImageList().isEmpty() ? null : ideaMarket.getImageList().get(0))
            .specialization(ideaMarket.getSpecialization())
            .purchasers(purchasers) // 구매자 정보
            .build();
    }
}
