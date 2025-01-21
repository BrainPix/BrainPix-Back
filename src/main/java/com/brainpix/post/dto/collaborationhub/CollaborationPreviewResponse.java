package com.brainpix.post.dto.collaborationhub;

import java.time.LocalDateTime;
import java.util.Map;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.profile.entity.Specialization;

public record CollaborationPreviewResponse(
	Long id,                  // 게시물 ID
	String writerName,        // 작성자 닉네임
	String title,             // 제목
	String imageUrl,          // 대표 이미지 URL
	LocalDateTime deadline,   // 마감일
	Specialization specialization, // 카테고리(분야)
	Long saveCount,           // 저장 횟수
	Long viewCount,           // 조회수
	Long totalParticipants,   // 총 모집 인원
	Long occupiedParticipants // 현재 모집된 인원
) {

	public static CollaborationPreviewResponse from(CollaborationHub hub, long saveCount) {
		Map<String, Long> participants = hub.calculateTotalAndOccupiedParticipants();

		return new CollaborationPreviewResponse(
			hub.getId(),
			hub.getWriter().getName(),
			hub.getTitle(),
			hub.getImageList().isEmpty() ? null : hub.getImageList().get(0),
			hub.getDeadline(),
			hub.getSpecialization(),
			saveCount,
			hub.getViewCount(),
			participants.getOrDefault("total", 0L),
			participants.getOrDefault("occupied", 0L)
		);
	}
}
