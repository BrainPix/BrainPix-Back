package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.post.converter.GetCollaborationHubListDtoConverter;
import com.brainpix.post.dto.GetCollaborationHubListDto;
import com.brainpix.post.repository.CollaborationHubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubService {

	private final CollaborationHubRepository collaborationHubRepository;

	@Transactional(readOnly = true)
	public GetCollaborationHubListDto.Response getCollaborationHubList(GetCollaborationHubListDto.Parameter parameter) {

		// 협업 게시글-저장수 쌍으로 반환된 결과
		Page<Object[]> result = collaborationHubRepository.findCollaborationListWithSaveCount(
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(),
			parameter.getPageable());

		return GetCollaborationHubListDtoConverter.toResponse(result);
	}
}
