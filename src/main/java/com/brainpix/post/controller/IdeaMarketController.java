package com.brainpix.post.controller;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.ideamarket.IdeaMarketDetailResponse;
import com.brainpix.post.dto.ideamarket.IdeaMarketPreviewResponse;
import com.brainpix.post.service.IdeaMarketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/myinfo/idea-market")
@RequiredArgsConstructor
public class IdeaMarketController {

    private final IdeaMarketService ideaMarketService;

    /**
     * 본인 게시물 - 아이디어 마켓 미리보기
     * @param userId 사용자 ID
     * @return 아이디어 마켓 게시물 리스트
     */
    @GetMapping
    public ApiResponse<List<IdeaMarketPreviewResponse>> getMyIdeaMarketPosts(@RequestParam Long userId) {
        List<IdeaMarketPreviewResponse> previews = ideaMarketService.getMyIdeaMarketPosts(userId);
        return ApiResponse.success(previews);
    }

    //@GetMapping
    //public ApiResponse<List<IdeaMarketPreviewResponse>> getMyIdeaMarketPosts(
    //    @AuthenticationPrincipal CustomUserDetails userDetails) {
    //    Long userId = userDetails.getId();
    //    List<IdeaMarketPreviewResponse> previews = ideaMarketService.getMyIdeaMarketPosts(userId);
    //    return ApiResponse.success(previews);
    //}

    /**
     * 본인 게시물 - 아이디어 마켓 상세보기
     * @param postId 게시물 ID
     * @return 아이디어 마켓 게시물 상세 정보
     */
    @GetMapping("/{postId}")
    public ApiResponse<IdeaMarketDetailResponse> getIdeaMarketDetail(@PathVariable Long postId) {
        IdeaMarketDetailResponse detail = ideaMarketService.getIdeaMarketDetail(postId);

        return ApiResponse.success(detail);
    }
}
