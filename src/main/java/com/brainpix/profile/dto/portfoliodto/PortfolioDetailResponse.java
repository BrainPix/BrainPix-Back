package com.brainpix.profile.dto.portfoliodto;

import com.brainpix.profile.entity.Specialization;
import java.time.YearMonth;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioDetailResponse {
    private Long id;
    private String title;
    private List<Specialization> specializations;
    private YearMonth startDate;
    private YearMonth endDate;
    private String content;
    private String imageUrl;
}

