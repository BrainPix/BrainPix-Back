package com.brainpix.post.controller;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.requesttask.RequestTaskDetailResponse;
import com.brainpix.post.dto.requesttask.RequestTaskPreviewResponse;
import com.brainpix.post.service.RequestTaskAcceptService;
import com.brainpix.post.service.RequestTaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/myinfo/request-task")
@RequiredArgsConstructor
public class RequestTaskController {

    private final RequestTaskService requestTaskService;
    private final RequestTaskAcceptService requestTaskAcceptService;

    /**
     * 본인 게시물 - 요청과제 미리보기
     */
    @GetMapping
    public ApiResponse<List<RequestTaskPreviewResponse>> getMyRequestTasks(@RequestParam Long userId) {
        List<RequestTaskPreviewResponse> previews = requestTaskService.getMyRequestTasks(userId);
        return ApiResponse.success(previews);
    }

    /**
     * 본인 게시물 - 요청과제 상세보기
     */
    @GetMapping("/{taskId}")
    public ApiResponse<RequestTaskDetailResponse> getRequestTaskDetail(@PathVariable Long taskId) {
        RequestTaskDetailResponse detail = requestTaskService.getRequestTaskDetail(taskId);
        return ApiResponse.success(detail);
    }



    /**
     * 지원자 수락
     * @param purchaseId 지원 ID
     */
    @PostMapping("/{purchaseId}/accept")
    public ApiResponse<Void> acceptApplicant(@PathVariable Long purchaseId) {
        requestTaskAcceptService.acceptApplicant(purchaseId);
        return ApiResponse.successWithNoData();
    }

    /**
     * 지원자 거절
     * @param purchaseId 지원 ID
     */
    @PostMapping("/{purchaseId}/reject")
    public ApiResponse<Void> rejectApplicant(@PathVariable Long purchaseId) {
        requestTaskAcceptService.rejectApplicant(purchaseId);
        return ApiResponse.successWithNoData();
    }
}
