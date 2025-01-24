package com.brainpix.post.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.converter.GetIdeaCommentListDtoConverter;
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaCommentListDto;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.service.IdeaMarketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ideas")
@RequiredArgsConstructor
@Slf4j
public class IdeaMarketController {

	private final IdeaMarketService ideaMarketService;

	@PostMapping
	public ResponseEntity<ApiResponse> createIdeaMarket(@RequestBody IdeaMarketCreateDto createDto) {
		Long ideaId = ideaMarketService.createIdeaMarket(createDto);
		return ResponseEntity.ok(ApiResponse.success(Map.of("ideaId", ideaId)));
	@GetMapping
	public ResponseEntity<ApiResponse<GetIdeaListDto.Response>> getIdeaList(
		GetIdeaListDto.Request request,
		Pageable pageable
	) {
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

	@PatchMapping("/{ideaId}")
	public ResponseEntity<ApiResponse> updateIdeaMarket(@PathVariable("ideaId") Long id, @RequestBody IdeaMarketUpdateDto updateDto) {
		ideaMarketService.updateIdeaMarket(id, updateDto);
		return ResponseEntity.ok(ApiResponse.success("아이디어 마켓 게시글이 성공적으로 수정되었습니다."));
	@GetMapping("/{ideaId}/comments")
	public ResponseEntity<ApiResponse<GetIdeaCommentListDto.Response>> getIdeaCommentList(
		@PathVariable("ideaId") Long ideaId,
		Pageable pageable
	) {
		GetIdeaCommentListDto.Parameter parameter = GetIdeaCommentListDtoConverter.toParameter(ideaId, pageable);
		GetIdeaCommentListDto.Response response = ideaMarketService.getIdeaCommentList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@DeleteMapping("/{ideaId}")
	public ResponseEntity<ApiResponse> deleteIdeaMarket(@PathVariable("ideaId") Long id) {
		ideaMarketService.deleteIdeaMarket(id);
		return ResponseEntity.ok(ApiResponse.success("아이디어 마켓 게시글이 성공적으로 삭제되었습니다."));
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
