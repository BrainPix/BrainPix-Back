package com.brainpix.joining.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CollectionErrorCode;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.converter.CollectionGatheringConverter;
import com.brainpix.joining.dto.AcceptedCollaborationDto;
import com.brainpix.joining.dto.RejectedCollaborationDto;
import com.brainpix.joining.dto.TeamMemberInfoDto;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.joining.util.PageableUtils;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupportCollaborationService {

	private final CollectionGatheringRepository gatheringRepository;
	private final CollectionGatheringConverter converter;
	private final UserRepository userRepository;

	/**
	 * [1] 거절 목록 조회
	 * - accepted = false
	 * - joiner = currentUser
	 */

	@Transactional(readOnly = true)
	public Page<RejectedCollaborationDto> getRejectedList(Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		Pageable sortedPageable = PageableUtils.withSort(pageable, "createdAt", Sort.Direction.DESC);

		Page<CollectionGathering> rejectedPage =
			gatheringRepository.findByJoinerAndAcceptedIsFalse(currentUser, sortedPageable);

		return rejectedPage.map(converter::toRejectedDto);
	}

	/**
	 * [2] 수락 목록 조회
	 * - accepted = true
	 * - joiner = currentUser
	 * - 팀원 정보까지 추가
	 */
	@Transactional(readOnly = true)
	public Page<AcceptedCollaborationDto> getAcceptedList(Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		Pageable sortedPageable = PageableUtils.withSort(pageable, "createdAt", Sort.Direction.DESC);

		Page<CollectionGathering> acceptedPage =
			gatheringRepository.findByJoinerAndAcceptedIsTrue(currentUser, sortedPageable);

		return acceptedPage.map(cg -> {
			CollaborationHub hub = cg.getCollaborationRecruitment().getParentCollaborationHub();
			List<TeamMemberInfoDto> teamInfo = converter.createTeamInfoList(hub);
			return converter.toAcceptedDto(cg, teamInfo);
		});
	}

	/**
	 * [3] 거절 항목 삭제 (DB 물리 삭제했습니다.)
	 */
	@Transactional
	public void deleteRejected(Long userId, Long collectionGatheringId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		CollectionGathering collectionGathering = gatheringRepository.findById(collectionGatheringId)
			.orElseThrow(() -> new BrainPixException(CollectionErrorCode.COLLECTION_NOT_FOUND));

		collectionGathering.validateJoiner(currentUser);
		collectionGathering.validateRejectedStatus();

		gatheringRepository.delete(collectionGathering);

	}
}
