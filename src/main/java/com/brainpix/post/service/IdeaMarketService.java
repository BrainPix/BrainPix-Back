package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.repository.IdeaMarketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdeaMarketService {

	private final IdeaMarketRepository ideaMarketRepository;

	// 아이디어 메인페이지에서 검색 조건을 적용하여 아이디어 목록을 반환합니다.
	@Transactional(readOnly = true)
	public GetIdeaListDto.Response getIdeaList(GetIdeaListDto.Request request, Pageable pageable) {

		// 아이디어-저장수 쌍으로 반환된 결과
		Page<Object[]> result = ideaMarketRepository.findIdeaListWithSaveCount(request.getType(),
			request.getKeyword(), request.getCategory(), request.getOnlyCompany(), request.getSortType(), pageable);

		return GetIdeaListDtoConverter.toResponse(result);
	}
}
