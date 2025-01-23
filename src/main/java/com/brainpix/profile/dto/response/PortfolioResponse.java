package com.brainpix.profile.dto.response;

import com.brainpix.profile.entity.Portfolio;
import java.time.LocalDateTime;

public record PortfolioResponse(long id,
                                String title,
                                LocalDateTime createdDate
) {

    public static PortfolioResponse from(Portfolio portfolio) {
        return new PortfolioResponse(
            portfolio.getId(),
            portfolio.getTitle(),
            portfolio.getCreatedAt()
        );
    }
}
