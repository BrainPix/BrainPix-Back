package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.dto.PostApiResponseDto;
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
@Tag(name = "아이디어 마켓 생성, 수정, 삭제 API", description = "아이디어 마켓 관련 API")
public class IdeaMarketCommandController {

	private final IdeaMarketService ideaMarketService;

	@AllUser
	@Operation(summary = "아이디어 마켓 글 생성", description = "아이디어 마켓 게시글을 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<PostApiResponseDto>> createIdeaMarket(@UserId Long userId,
		@Valid @RequestBody IdeaMarketCreateDto createDto) {
		Long ideaId = ideaMarketService.createIdeaMarket(userId, createDto);
		return ResponseEntity.ok(ApiResponse.success(new PostApiResponseDto("ideaId", ideaId)));
	}

	@AllUser
	@Operation(summary = "아이디어 마켓 글 수정", description = "아이디어 마켓 게시글을 수정합니다.")
	@PutMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<Void>> updateIdeaMarket(@PathVariable("ideaId") Long ideaId,
		@UserId Long userId, @Valid @RequestBody IdeaMarketUpdateDto updateDto) {
		ideaMarketService.updateIdeaMarket(ideaId, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@Operation(summary = "아이디어 마켓 글 삭제", description = "아이디어 마켓 게시글을 삭제합니다.")
	@DeleteMapping("/{ideaId}")
	public ResponseEntity<ApiResponse<Void>> deleteIdeaMarket(@PathVariable("ideaId") Long ideaId,
		@UserId Long userId) {
		ideaMarketService.deleteIdeaMarket(ideaId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
