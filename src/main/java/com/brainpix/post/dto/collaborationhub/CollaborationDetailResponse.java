package com.brainpix.post.dto.collaborationhub;

import com.brainpix.joining.dto.RecruitmentApplicantsResponse;
import com.brainpix.joining.dto.RecruitmentDetailResponse;
import com.brainpix.post.entity.request_task.CollaborationType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollaborationDetailResponse {
    private Long id; // 게시물 ID
    private String title; // 제목
    private String category; // 카테고리
    private String imageUrl; // 대표 이미지 URL
    private LocalDateTime deadline; // 마감일
    private String link; // 관련 링크
    private CollaborationType collaborationType; // 협업 유형
    private List<RecruitmentDetailResponse> recruitments; // 모집 분야
    private List<RecruitmentApplicantsResponse> applicants; // 모집 분야별 지원자 정보
}
