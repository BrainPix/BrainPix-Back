package com.brainpix.profile.controller;



import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;


    @PostMapping
    public ResponseEntity<Long> createPortfolio(
        @RequestParam long userId,
        @RequestBody PortfolioRequest request
    ) {
        Long portfolioId = portfolioService.createPortfolio(userId, request);
        return ResponseEntity.ok(portfolioId);
    }

    @PutMapping("/{portfolioId}")
    public ResponseEntity<Void> updatePortfolio(
        @RequestParam long userId,
        @PathVariable long portfolioId,
        @RequestBody PortfolioRequest request
    ) {
        portfolioService.updatePortfolio(userId, portfolioId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<PortfolioResponse>> findMyPortfolios(
        @RequestParam long userId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PortfolioResponse> result = portfolioService.findAllMyPortfolios(userId, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioDetailResponse> findPortfolioDetail(
        @RequestParam long userId,
        @PathVariable long portfolioId
    ) {
        PortfolioDetailResponse detail = portfolioService.findPortfolioDetail(userId, portfolioId);
        return ResponseEntity.ok(detail);
    }
}
