package com.brainpix.post.dto.collaborationhub;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollaborationPreviewResponse {

    private Long id; // 게시물 ID
    private String nickname; // 작성자 닉네임
    private String title; // 제목
    private String imageUrl; // 대표 이미지 URL
    private long daysLeft; // D-Day
    private Long currentParticipants; // 현재 모집된 인원
    private Long totalParticipants; // 총 모집 인원

}
