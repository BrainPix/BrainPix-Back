package com.brainpix.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.MyCollaborationPostConverter;
import com.brainpix.post.dto.mypostdto.CollaborationCurrentMemberInfo;
import com.brainpix.post.dto.mypostdto.CollaborationSupportInfo;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubDetailDto;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubListDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.post.repository.SavedPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MyCollaborationPostManagementService {

	private final CollaborationHubRepository hubRepository;
	private final SavedPostRepository savedPostRepository;
	private final MyCollaborationPostConverter converter;
	private final CollectionGatheringRepository collectionGatheringRepository;

	@Transactional(readOnly = true)
	public Page<MyCollaborationHubListDto> getAllCollaborationHubs(Long userId, Pageable pageable) {
		// 1) 해당 작성자의 협업 광장 게시글 목록을 페이징 조회
		return hubRepository.findByWriterId(userId, pageable)
			.map(hub -> {
				// 2) 각 게시글의 저장 횟수, 현재 인원, 전체 인원을 계산
				long savedCount = savedPostRepository.countByPostId(hub.getId());
				long currentMembers = hub.getCollaborations().stream()
					.mapToLong(rec -> rec.getGathering().getOccupiedQuantity())
					.sum();
				long totalMembers = hub.getCollaborations().stream()
					.mapToLong(rec -> rec.getGathering().getTotalQuantity())
					.sum();
				// 3) DTO로 변환
				return converter.toCollaborationHubListDto(hub, savedCount, currentMembers, totalMembers);
			});
	}

	@Transactional(readOnly = true)
	public MyCollaborationHubDetailDto getCollaborationHubDetail(Long hubId) {
		CollaborationHub hub = hubRepository.findById(hubId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.COLLABORATION_HUB_NOT_FOUND));

		// 모집 정보를 기반으로 지원 현황 조회 (대기 중인 지원자)
		List<CollaborationSupportInfo> supportInfos = hub.getCollaborations().stream()
			.flatMap(rec -> collectionGatheringRepository.findWaitingByRecruitment(rec).stream()
				.map(gathering -> converter.toSupportInfo(
					gathering.getJoiner(),
					rec.getDomain(),
					gathering.getCollaborationRecruitment().getGathering().getOccupiedQuantity(),
					gathering.getCollaborationRecruitment().getGathering().getTotalQuantity()
				))
			)
			.collect(Collectors.toList());

		// 모집 정보를 기반으로 현재 인원 조회 (수락된 지원자)
		List<CollaborationCurrentMemberInfo> currentMembers = hub.getCollaborations().stream()
			.flatMap(rec -> collectionGatheringRepository.findAcceptedByRecruitment(rec).stream()
				.map(gathering -> converter.toCurrentMemberInfo(
					gathering.getJoiner(),
					rec.getDomain(),
					gathering.getCollaborationRecruitment().getGathering().getOccupiedQuantity()
				))
			)
			.collect(Collectors.toList());

		return converter.toCollaborationHubDetailDto(hub, supportInfos, currentMembers);
	}
}
