package com.brainpix.post.controller;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.collaborationhub.CollaborationDetailResponse;
import com.brainpix.post.dto.collaborationhub.CollaborationPreviewResponse;
import com.brainpix.post.service.CollaborationAcceptService;
import com.brainpix.post.service.CollaborationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage/collaboration")
@RequiredArgsConstructor
public class CollaborationController {

    private final CollaborationService collaborationService;
    private final CollaborationAcceptService collaborationAcceptService;

    /**
     * 협업광장 게시물 미리보기
     * @param userId 사용자 ID
     * @return 협업광장 게시물 미리보기 리스트
     */
    @GetMapping
    public ApiResponse<List<CollaborationPreviewResponse>> getCollaborationPreviews(@RequestParam Long userId) {
        List<CollaborationPreviewResponse> previews = collaborationService.getCollaborationPreviews(userId);
        return ApiResponse.success(previews);
    }


    /**
     * 본인 게시물 - 협업광장 상세보기
     * @param collaborationId 게시물 ID
     * @return 협업광장 상세 정보
     */
    @GetMapping("/{collaborationId}")
    public ApiResponse<CollaborationDetailResponse> getCollaborationDetail(@PathVariable Long collaborationId) {
        CollaborationDetailResponse detail = collaborationService.getCollaborationDetail(collaborationId);
        return ApiResponse.success(detail);
    }



    /**
     * 지원자 수락 API
     * @param gatheringId CollectionGathering 테이블의 PK(지원자 정보 ID)
     */
    @PostMapping("/accept")
    public ApiResponse<Void> acceptApplicant(@RequestParam Long gatheringId) {
        collaborationAcceptService.acceptApplicant(gatheringId);
        return ApiResponse.successWithNoData();
    }

    /**
     * 지원자 거절 API
     * @param gatheringId CollectionGathering 테이블의 PK(지원자 정보 ID)
     */
    @PostMapping("/reject")
    public ApiResponse<Void> rejectApplicant(@RequestParam Long gatheringId) {
        collaborationAcceptService.rejectApplicant(gatheringId);
        return ApiResponse.successWithNoData();
    }
}