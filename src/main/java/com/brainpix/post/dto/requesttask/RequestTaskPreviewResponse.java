package com.brainpix.post.dto.requesttask;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTaskPreviewResponse {
    private Long id;
    private String nickname; // 작성자 닉네임
    private String profileImage; // 작성자 프로필 이미지
    private String title; // 제목
    private String imageUrl; // 게시물 이미지
    private long daysLeft; // D-Day 남은 날짜
}
