package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.service.InitialCollectionGatheringService;
import com.brainpix.post.converter.CreateCollaborationHubInitialMemberConverter;
import com.brainpix.post.dto.CollaborationHubInitialMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRecruitmentRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubInitialMemberService {

	private final CollaborationHubRecruitmentRepository recruitmentRepository;
	private final UserRepository userRepository;
	private final CreateCollaborationHubInitialMemberConverter createInitialMemberConverter;
	private final InitialCollectionGatheringService initialCollectionGatheringService;

	@Transactional
	public void createInitialMembers(CollaborationHub collaborationHub,
		List<CollaborationHubInitialMemberDto> initialMemberDtos) {

		List<CollaborationRecruitment> initialMembers = new ArrayList<>();

		for (CollaborationHubInitialMemberDto initialMemberDto : initialMemberDtos) {

			User joiner = userRepository.findByIdentifier(initialMemberDto.getIdentifier())
				.orElseThrow(
					() -> new BrainPixException(
						PostErrorCode.USER_NOT_FOUND)); // "개최 인원에 해당하는 유저를 찾을 수 없습니다." // 인원

			CollaborationRecruitment recruitment = createInitialMemberConverter.convertToInitialMember(
				collaborationHub, initialMemberDto);

			recruitmentRepository.save(recruitment);

			initialCollectionGatheringService.CreateInitialGathering(joiner, recruitment);

			initialMembers.add(recruitment);
		}
	}

	//개최 인원 아이디 검증
	public void validateUserIdentifier(String identifier) {
		boolean exists = userRepository.existsByIdentifier(identifier);

		if (!exists) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND); // "해당 ID의 유저를 찾을 수 없습니다.");
		}
	}
}
