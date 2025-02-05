package com.brainpix.profile.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.profile.dto.CreatePortfolioDto;
import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.service.PortfolioService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/portfolios")
@Tag(name = "Portfolio API", description = "포트폴리오 생성, 수정, 삭제 및 조회 관련 API")
@RequiredArgsConstructor
public class PortfolioController {

	private final PortfolioService portfolioService;

	@AllUser
	@Operation(summary = "포트폴리오 생성", description = "사용자 ID와 포트폴리오 요청 데이터를 받아 포트폴리오를 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<CreatePortfolioDto.Response>> createPortfolio(
		@UserId Long userId,
		@Valid @RequestBody PortfolioRequest request
	) {
		Long portfolioId = portfolioService.createPortfolio(userId, request);

		CreatePortfolioDto.Response response = new CreatePortfolioDto.Response(portfolioId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "포트폴리오 수정", description = "사용자 ID와 포트폴리오 ID, 그리고 수정할 데이터를 받아 포트폴리오를 수정합니다.")
	@PutMapping("/{portfolioId}")
	public ResponseEntity<ApiResponse<Void>> updatePortfolio(
		@UserId Long userId,
		@PathVariable long portfolioId,
		@Valid @RequestBody PortfolioRequest request
	) {
		try {
			portfolioService.updatePortfolio(userId, portfolioId, request);
			return ResponseEntity.ok(ApiResponse.successWithNoData());
		} finally {

		}
	}

	@AllUser
	@Operation(summary = "포트폴리오 삭제", description = "사용자 ID와 포트폴리오 ID를 받아 해당 포트폴리오를 삭제합니다.")
	@DeleteMapping("/{portfolioId}")
	public ResponseEntity<ApiResponse<Void>> deletePortfolio(
		@UserId Long userId,
		@PathVariable long portfolioId
	) {
		portfolioService.deletePortfolio(userId, portfolioId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@Operation(summary = "내 포트폴리오 목록 조회", description = "사용자 ID를 기준으로 포트폴리오 목록을 페이징 처리하여 조회합니다.")
	@GetMapping
	public ResponseEntity<CommonPageResponse<PortfolioResponse>> findMyPortfolios(
		@UserId Long userId,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<PortfolioResponse> page = portfolioService.findAllMyPortfolios(userId, pageable);

		CommonPageResponse<PortfolioResponse> response = CommonPageResponse.of(page);

		return ResponseEntity.ok(response);
	}

	@AllUser
	@Operation(summary = "포트폴리오 상세 조회", description = "사용자 ID와 포트폴리오 ID를 받아 포트폴리오의 상세 정보를 조회합니다.")
	@GetMapping("/{portfolioId}")
	public ResponseEntity<ApiResponse<PortfolioDetailResponse>> findPortfolioDetail(
		@UserId Long userId,
		@PathVariable long portfolioId
	) {
		PortfolioDetailResponse detail = portfolioService.findPortfolioDetail(userId, portfolioId);
		return ResponseEntity.ok(ApiResponse.success(detail));
	}
}
