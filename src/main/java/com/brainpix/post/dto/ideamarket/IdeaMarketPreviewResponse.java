package com.brainpix.post.dto.ideamarket;

import com.brainpix.post.entity.idea_market.IdeaMarketAuth;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaMarketPreviewResponse {
    private Long id;
    private String nickname;
    private IdeaMarketAuth ideaMarketAuth;
    private String title;
    private Long price;
    private String imageUrl;
    private String profileImage;

}
