package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.dto.IdeaMarketApiResponseDto;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.service.IdeaMarketService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/idea-markets")
@RequiredArgsConstructor
@Slf4j
public class IdeaMarketController {

	private final IdeaMarketService ideaMarketService;

	@PostMapping
	public ResponseEntity<ApiResponse<IdeaMarketApiResponseDto>> createIdeaMarket(@RequestParam Long userId,
		@Valid @RequestBody IdeaMarketCreateDto createDto) {
		Long ideaId = ideaMarketService.createIdeaMarket(userId, createDto);
		return ResponseEntity.ok(ApiResponse.success(new IdeaMarketApiResponseDto("ideaId", ideaId)));
	}

	@PutMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<Void>> updateIdeaMarket(@PathVariable("ideaId") Long ideaId,
		@RequestParam Long userId, @Valid @RequestBody IdeaMarketUpdateDto updateDto) {
		ideaMarketService.updateIdeaMarket(ideaId, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@DeleteMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<Void>> deleteIdeaMarket(@PathVariable("ideaId") Long ideaId,
		@RequestParam Long userId) {
		ideaMarketService.deleteIdeaMarket(ideaId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "아이디어 전체 조회", description = "쿼리 파라미터로 아이디어 마켓 타입(IDEA_SOLUTION, MARKET_PLACE)과 검색 조건, page, size를 입력받아 전체 조회합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<GetIdeaListDto.IdeaDetail>>> getIdeaList(
		GetIdeaListDto.Request request,
		@PageableDefault(page = 0, size = 6) Pageable pageable) {
		GetIdeaListDto.Parameter parameter = GetIdeaListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetIdeaListDto.IdeaDetail> response = ideaMarketService.getIdeaList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "아이디어 상세 조회", description = "경로 변수로 아이디어 마켓 식별자 ID를 입력받아 상세 조회합니다.")
	@GetMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<GetIdeaDetailDto.Response>> getIdeaDetail(
		@UserId Long userId,
		@PathVariable("ideaId") Long ideaId
	) {
		GetIdeaDetailDto.Parameter parameter = GetIdeaDetailDtoConverter.toParameter(ideaId, userId);
		GetIdeaDetailDto.Response response = ideaMarketService.getIdeaDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
	
	@Operation(summary = "인기 아이디어 조회", description = "쿼리 파라미터로 아이디어 마켓 타입(IDEA_SOLUTION, MARKET_PLACE)과 page, size를 입력받아 인기 아이디어를 조회합니다.")
	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetPopularIdeaListDto.IdeaDetail>>> getPopularIdeaList(
		GetPopularIdeaListDto.Request request,
		@PageableDefault(page = 0, size = 3) Pageable pageable
	) {
		GetPopularIdeaListDto.Parameter parameter = GetPopularIdeaListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetPopularIdeaListDto.IdeaDetail> response = ideaMarketService.getPopularIdeaList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
