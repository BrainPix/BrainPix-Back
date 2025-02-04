package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.joining.service.GatheringService;
import com.brainpix.post.converter.CreateCollaborationHubRecruitmentConverter;
import com.brainpix.post.dto.CollaborationRecruitmentDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRecruitmentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubRecruitmentService {

	private final CollaborationHubRecruitmentRepository recruitmentRepository;
	private final GatheringService gatheringService;
	private final CreateCollaborationHubRecruitmentConverter createCollaborationHubRecruitmentConverter;

	@Transactional
	public void createRecruitments(CollaborationHub collaborationHub,
		List<CollaborationRecruitmentDto> recruitmentDtos) {

		List<CollaborationRecruitment> recruitments = new ArrayList<>();

		for (CollaborationRecruitmentDto recruitmentDto : recruitmentDtos) {
			// 모집 인원 정보 생성
			Gathering gathering = gatheringService.createGathering(recruitmentDto.getGatheringDto());

			CollaborationRecruitment recruitment = createCollaborationHubRecruitmentConverter.convertToCollaborationRecruitment(
				collaborationHub, recruitmentDto, gathering);

			recruitments.add(recruitment);

		}
		recruitmentRepository.saveAll(recruitments);
	}
}


