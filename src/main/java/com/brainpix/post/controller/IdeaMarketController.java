package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
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
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.dto.PostApiResponseDto;
import com.brainpix.post.service.IdeaMarketService;

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
	public ResponseEntity<ApiResponse<PostApiResponseDto>> createIdeaMarket(@RequestParam Long userId,
		@Valid @RequestBody IdeaMarketCreateDto createDto) {
		Long ideaId = ideaMarketService.createIdeaMarket(userId, createDto);
		return ResponseEntity.ok(ApiResponse.success(new PostApiResponseDto("ideaId", ideaId)));
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

	@GetMapping
	public ResponseEntity<ApiResponse<GetIdeaListDto.Response>> getIdeaList(GetIdeaListDto.Request request,
		Pageable pageable) {
		GetIdeaListDto.Parameter parameter = GetIdeaListDtoConverter.toParameter(request, pageable);
		GetIdeaListDto.Response response = ideaMarketService.getIdeaList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<GetIdeaDetailDto.Response>> getIdeaDetail(
		@PathVariable("ideaId") Long ideaId
	) {
		GetIdeaDetailDto.Parameter parameter = GetIdeaDetailDtoConverter.toParameter(ideaId);
		GetIdeaDetailDto.Response response = ideaMarketService.getIdeaDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<GetPopularIdeaListDto.Response>> getPopularIdeaList(
		GetPopularIdeaListDto.Request request,
		Pageable pageable
	) {
		GetPopularIdeaListDto.Parameter parameter = GetPopularIdeaListDtoConverter.toParameter(request, pageable);
		GetPopularIdeaListDto.Response response = ideaMarketService.getPopularIdeaList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
