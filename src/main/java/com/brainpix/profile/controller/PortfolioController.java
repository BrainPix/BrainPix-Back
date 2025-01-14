package com.brainpix.profile.controller;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioCreateRequest;
import com.brainpix.profile.dto.portfoliodto.PortfolioDetailResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioListResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioUpdateRequest;
import com.brainpix.profile.service.portfolio.PortfolioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * 포트폴리오 리스트 조회
     * @param userId 사용자 ID
     * @return 포트폴리오 리스트
     */
    @GetMapping("/users/{userId}")
    public ApiResponse<List<PortfolioListResponse>> getPortfolioList(@RequestParam Long userId) {
        List<PortfolioListResponse> portfolios = portfolioService.getPortfolioList(userId);
        return ApiResponse.success(portfolios);
    }

    /**
     * 포트폴리오 상세 조회
     * @param portfolioId 포트폴리오 ID
     * @return 포트폴리오 상세 정보
     */
    @GetMapping("/{portfolioId}")
    public ApiResponse<PortfolioDetailResponse> getPortfolioDetail(@PathVariable Long portfolioId) {
        PortfolioDetailResponse portfolio = portfolioService.getPortfolioDetail(portfolioId);
        return ApiResponse.success(portfolio);
    }

    /**
     * 포트폴리오 추가
     * @param userId 사용자 ID
     * @param request 포트폴리오 추가 요청 데이터
     */
    @PostMapping("/users/{userId}")
    public ApiResponse<Void> createPortfolio(@RequestParam Long userId, @RequestBody PortfolioCreateRequest request) {
        portfolioService.createPortfolio(userId, request);
        return ApiResponse.successWithNoData();
    }

    /**
     * 포트폴리오 수정
     * @param portfolioId 포트폴리오 ID
     * @param request 포트폴리오 수정 요청 데이터
     */
    @PatchMapping("/{portfolioId}")
    public ApiResponse<Void> updatePortfolio(@PathVariable Long portfolioId, @RequestBody PortfolioUpdateRequest request) {
        portfolioService.updatePortfolio(portfolioId, request);
        return ApiResponse.successWithNoData();
    }


    /**
     * 포트폴리오 삭제
     * @param portfolioId 포트폴리오 ID
     */
    @DeleteMapping("/{portfolioId}")
    public ApiResponse<Void> deletePortfolio(@PathVariable Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ApiResponse.successWithNoData();
    }
}
