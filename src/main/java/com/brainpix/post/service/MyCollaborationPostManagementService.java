package com.brainpix.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.MyCollaborationPostConverter;
import com.brainpix.post.dto.CollaborationCurrentMemberInfo;
import com.brainpix.post.dto.CollaborationSupportInfo;
import com.brainpix.post.dto.MyCollaborationHubDetailDto;
import com.brainpix.post.dto.MyCollaborationHubListDto;
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
	public List<MyCollaborationHubListDto> getAllCollaborationHubs(Long userId) {
		return hubRepository.findByWriterId(userId).stream()
			.map(hub -> {
				long savedCount = savedPostRepository.countByPostId(hub.getId());
				long currentMembers = hub.getCollaborations().stream()
					.mapToLong(rec -> rec.getGathering().getOccupiedQuantity())
					.sum();
				long totalMembers = hub.getCollaborations().stream()
					.mapToLong(rec -> rec.getGathering().getTotalQuantity())
					.sum();
				return converter.toCollaborationHubListDto(hub, savedCount, currentMembers, totalMembers);
			})
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public MyCollaborationHubDetailDto getCollaborationHubDetail(Long hubId) {
		CollaborationHub hub = hubRepository.findById(hubId)
			.orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

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
