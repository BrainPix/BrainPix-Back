package com.brainpix.joining.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.converter.CollectionGatheringConverter;
import com.brainpix.joining.dto.AcceptedCollaborationDto;
import com.brainpix.joining.dto.RejectedCollaborationDto;
import com.brainpix.joining.dto.TeamMemberInfoDto;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
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
	public List<RejectedCollaborationDto> getRejectedList(Long userId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

		// "거절" 상태: accepted = false
		List<CollectionGathering> rejectedList =
			gatheringRepository.findByJoinerAndAcceptedIsFalse(currentUser);

		// 엔티티 -> DTO 변환
		return rejectedList.stream()
			.map(converter::toRejectedDto)
			.collect(Collectors.toList());
	}

	/**
	 * [2] 수락 목록 조회
	 * - accepted = true
	 * - joiner = currentUser
	 * - 팀원 정보까지 추가
	 */
	@Transactional(readOnly = true)
	public List<AcceptedCollaborationDto> getAcceptedList(Long userId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

		// "수락" 상태: accepted = true
		List<CollectionGathering> acceptedList =
			gatheringRepository.findByJoinerAndAcceptedIsTrue(currentUser);

		// 각 CollectionGathering 마다,
		//  -> 해당 CollaborationHub의 teamInfoList를 구성
		return acceptedList.stream()
			.map(cg -> {
				CollaborationHub hub = cg.getCollaborationRecruitment()
					.getParentCollaborationHub();
				// 팀원 정보 생성
				List<TeamMemberInfoDto> teamInfo =
					converter.createTeamInfoList(hub);

				// 최종 DTO
				return converter.toAcceptedDto(cg, teamInfo);
			})
			.collect(Collectors.toList());
	}

	/**
	 * [3] 거절 항목 삭제 (DB 물리 삭제했습니다.)
	 */
	@Transactional
	public void deleteRejected(Long userId, Long collectionGatheringId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

		CollectionGathering cg = gatheringRepository.findById(collectionGatheringId)
			.orElseThrow(() -> new RuntimeException("지원 내역이 존재하지 않습니다."));

		// 본인 항목인지 + 거절 상태인지 확인
		if (!cg.getJoiner().equals(currentUser)) {
			throw new RuntimeException("본인이 지원한 항목이 아닙니다.");
		}
		if (Boolean.TRUE.equals(cg.getAccepted())) {
			throw new RuntimeException("거절 상태가 아닌 항목은 삭제할 수 없습니다.");
		}

		gatheringRepository.delete(cg);
	}
}
