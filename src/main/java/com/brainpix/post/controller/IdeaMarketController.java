package com.brainpix.post.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.service.IdeaMarketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
public class IdeaMarketController {

	private final IdeaMarketService ideaMarketService;

	@PostMapping
	public ResponseEntity<ApiResponse> createIdeaMarket(@RequestBody IdeaMarketCreateDto createDto) {
		Long ideaId = ideaMarketService.createIdeaMarket(createDto);
		return ResponseEntity.ok(ApiResponse.success(Map.of("ideaId", ideaId)));
	}

	@PatchMapping("/{ideaId}")
	public ResponseEntity<ApiResponse> updateIdeaMarket(@PathVariable("ideaId") Long id, @RequestBody IdeaMarketUpdateDto updateDto) {
		ideaMarketService.updateIdeaMarket(id, updateDto);
		return ResponseEntity.ok(ApiResponse.success("아이디어 마켓 게시글이 성공적으로 수정되었습니다."));
	}

	@DeleteMapping("/{ideaId}")
	public ResponseEntity<ApiResponse> deleteIdeaMarket(@PathVariable("ideaId") Long id) {
		ideaMarketService.deleteIdeaMarket(id);
		return ResponseEntity.ok(ApiResponse.success("아이디어 마켓 게시글이 성공적으로 삭제되었습니다."));
	}
}
