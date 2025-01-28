package com.brainpix.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.MyCollaborationPostConverter;
import com.brainpix.post.dto.mypostdto.CollaborationCurrentMemberInfo;
import com.brainpix.post.dto.mypostdto.CollaborationSupportInfo;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubDetailDto;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubListDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
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

		// 모집 정보를 기반으로 현재 인원 조회 (수락된 지원자+초기멤버) (어지럽네요)
		List<CollaborationCurrentMemberInfo> currentMembers = hub.getCollaborations().stream()
			.flatMap(rec -> {
				// 1. 수락된 인원과 스타팅 멤버를 모두 가져오기
				List<CollectionGathering> acceptedAndStartingMembers = collectionGatheringRepository.findByRecruitmentAndAcceptedOrInitialGathering(
					rec);

				// 2. 변환 로직
				return acceptedAndStartingMembers.stream()
					.map(gathering -> converter.toCurrentMemberInfo(
						gathering.getJoiner(),
						rec.getDomain(),
						rec.getGathering().getOccupiedQuantity()
					));
			})
			.collect(Collectors.toList());

		return converter.toCollaborationHubDetailDto(hub, supportInfos, currentMembers);
	}

	/**
	 * 지원자 수락
	 */
	@Transactional
	public void acceptSupport(Long userId, Long gatheringId) {
		CollectionGathering gathering = collectionGatheringRepository.findById(gatheringId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		CollaborationRecruitment recruitment = gathering.getCollaborationRecruitment();
		CollaborationHub hub = recruitment.getParentCollaborationHub();

		// 작성자 확인
		hub.validateWriter(userId);

		// 지원자 수락
		gathering.accept();

		// 모집된 인원 수 증가
		Gathering gatheringInfo = recruitment.getGathering();
		gatheringInfo.increaseOccupiedQuantity(1L);

		// 저장
		collectionGatheringRepository.save(gathering);
	}

	/**
	 * 지원자 거절
	 */
	@Transactional
	public void rejectSupport(Long userId, Long gatheringId) {
		CollectionGathering gathering = collectionGatheringRepository.findById(gatheringId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		CollaborationRecruitment recruitment = gathering.getCollaborationRecruitment();
		CollaborationHub hub = recruitment.getParentCollaborationHub();

		// 작성자 확인
		hub.validateWriter(userId);

		// 지원자 수락
		gathering.reject();

		// 저장
		collectionGatheringRepository.save(gathering);
	}

}
