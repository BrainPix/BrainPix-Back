package com.brainpix.post.dto.ideamarket;

import com.brainpix.profile.entity.Specialization;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaMarketDetailResponse {
    private Long id;
    private String title;
    private Long price;
    private Specialization specialization;
    private String imageUrl;
    private List<IdeaMarketPurchasingResponse> purchasers; // 구매자 정보
}