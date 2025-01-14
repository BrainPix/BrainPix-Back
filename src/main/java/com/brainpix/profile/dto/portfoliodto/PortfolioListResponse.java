package com.brainpix.profile.dto.portfoliodto;

import com.brainpix.profile.entity.Specialization;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioListResponse {
    private Long id;
    private String imageUrl;
}
