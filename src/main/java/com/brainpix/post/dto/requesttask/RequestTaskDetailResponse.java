package com.brainpix.post.dto.requesttask;

import com.brainpix.joining.dto.RecruitmentApplicantsResponse;
import com.brainpix.joining.dto.RecruitmentDetailResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTaskDetailResponse {

    private Long id;
    private String title;
    private String category;
    private String imageUrl;
    private LocalDateTime deadline;
    private List<RecruitmentDetailResponse> recruitments;
    private List<RecruitmentApplicantsResponse> applicants;

}