package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.GetIdeaCommentListDto;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.service.IdeaMarketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
@Slf4j
public class IdeaMarketController {
	private final IdeaMarketService ideaMarketService;

	@GetMapping
	public ResponseEntity<ApiResponse<GetIdeaListDto.Response>> getIdeaList(
		GetIdeaListDto.Request request,
		Pageable pageable
	) {
		GetIdeaListDto.Response response = ideaMarketService.getIdeaList(request, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<GetIdeaDetailDto.Response>> getIdeaDetail(
		@PathVariable("ideaId") Long ideaId
	) {
		GetIdeaDetailDto.Response response = ideaMarketService.getIdeaDetail(ideaId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{ideaId}/comments")
	public ResponseEntity<ApiResponse<GetIdeaCommentListDto.Response>> getIdeaCommentList(
		@PathVariable("ideaId") Long ideaId,
		Pageable pageable
	) {
		GetIdeaCommentListDto.Response response = ideaMarketService.getIdeaCommentList(ideaId, pageable);		// post 서비스로 옮겨도 되겠다.
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<GetPopularIdeaListDto.Response>> getPopularIdeaList(
		GetPopularIdeaListDto.Request request,
		Pageable pageable
	) {
		GetPopularIdeaListDto.Response response = ideaMarketService.getPopularIdeaList(request, pageable);	// post 서비스로 옮겨도 되겠다.
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
