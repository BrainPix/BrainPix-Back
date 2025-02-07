package com.brainpix.post.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.swagger.SwaggerPageable;
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetIdeaPurchasePageDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetIdeaPurchasePageDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.service.IdeaMarketService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/idea-markets")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "아이디어 마켓 검색 API", description = "아이디어 마켓 관련 API")
public class IdeaMarketQueryController {

	private final IdeaMarketService ideaMarketService;

	@SwaggerPageable
	@Operation(summary = "아이디어 전체 조회 [POST]", description =
		"json body로 아이디어 마켓 타입과 검색 조건을 입력 받습니다."
			+ "<br>페이징을 위한 page, size는 쿼리 파라미터로 입력받아 전체 조회합니다."
			+ "<br>type : IDEA_SOLUTION, MARKET_PLACE"
			+ "<br>category : ADVERTISING_PROMOTION, DESIGN, LESSON, MARKETING, DOCUMENT_WRITING, MEDIA_CONTENT,"
			+ " TRANSLATION_INTERPRETATION, TAX_LAW_LABOR, CUSTOM_PRODUCTION, STARTUP_BUSINESS, FOOD_BEVERAGE, IT_TECH, OTHERS"
			+ "<br>sortType : NEWEST, OLDEST, POPULAR, HIGHEST_PRICE, LOWEST_PRICE")
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetIdeaListDto.IdeaDetail>>> getIdeaList(
		@RequestBody @Valid GetIdeaListDto.Request request,
		@PageableDefault(page = 0, size = 6) Pageable pageable) {
		GetIdeaListDto.Parameter parameter = GetIdeaListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetIdeaListDto.IdeaDetail> response = ideaMarketService.getIdeaList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "아이디어 상세 조회 [GET]", description = "경로 변수로 아이디어 마켓 식별자 ID를 입력받아 상세 조회합니다.")
	@GetMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<GetIdeaDetailDto.Response>> getIdeaDetail(
		@UserId Long userId,
		@PathVariable("ideaId") Long ideaId
	) {
		GetIdeaDetailDto.Parameter parameter = GetIdeaDetailDtoConverter.toParameter(ideaId, userId);
		GetIdeaDetailDto.Response response = ideaMarketService.getIdeaDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@SwaggerPageable
	@Operation(summary = "인기 아이디어 조회 [GET]", description = "쿼리 파라미터로 아이디어 마켓 타입과 페이징을 위한 page, size를 입력받아 인기 아이디어를 조회합니다.<br>type : IDEA_SOLUTION, MARKET_PLACE")
	@GetMapping("/search/popular")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetPopularIdeaListDto.IdeaDetail>>> getPopularIdeaList(
		@ModelAttribute @ParameterObject @Valid GetPopularIdeaListDto.Request request,
		@PageableDefault(page = 0, size = 3) Pageable pageable
	) {
		GetPopularIdeaListDto.Parameter parameter = GetPopularIdeaListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetPopularIdeaListDto.IdeaDetail> response = ideaMarketService.getPopularIdeaList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "아이디어 구매 페이지 [GET]", description = "경로 변수로 아이디어 식별자를 받아, 아이디어 구매 페이지에 필요한 DTO를 반환하는 API입니다.")
	@GetMapping("/{ideaId}/purchase")
	public ResponseEntity<ApiResponse<GetIdeaPurchasePageDto.Response>> getIdeaPurchasePage(
		@PathVariable("ideaId") Long ideaId, @UserId Long userId) {
		GetIdeaPurchasePageDto.Parameter parameter = GetIdeaPurchasePageDtoConverter.toParameter(ideaId, userId);
		GetIdeaPurchasePageDto.Response response = ideaMarketService.getIdeaPurchasePage(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
