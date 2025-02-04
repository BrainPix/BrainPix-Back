package com.brainpix.joining.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.AcceptedCollaborationDto;
import com.brainpix.joining.dto.RejectedCollaborationDto;
import com.brainpix.joining.dto.TeamMemberInfoDto;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;

@Component
public class CollectionGatheringConverter {

	/**
	 * [A] 거절된 협업 목록 DTO
	 */
	public RejectedCollaborationDto toRejectedDto(CollectionGathering cg) {
		CollaborationRecruitment recruitment = cg.getCollaborationRecruitment();
		CollaborationHub hub = recruitment.getParentCollaborationHub();

		return RejectedCollaborationDto.builder()
			.collectionGatheringId(cg.getId())
			.firstImage(hub.getFirstImage())                // Post#getFirstImage()
			.postCreatedAt(hub.getCreatedAt())            // BaseTimeEntity
			.postTitle(hub.getTitle())
			.postCategory("협업 광장 > " + hub.getSpecialization())
			.domain(recruitment.getDomain())
			.build();
	}

	/**
	 * [B] 수락된 협업 상세 DTO
	 *  - 본인이 지원한 CollectionGathering + 게시글 정보 + 팀원 정보
	 */
	public AcceptedCollaborationDto toAcceptedDto(CollectionGathering cg,
		List<TeamMemberInfoDto> teamInfoList) {
		CollaborationRecruitment recruitment = cg.getCollaborationRecruitment();
		CollaborationHub hub = recruitment.getParentCollaborationHub();

		// 작성자 정보
		User writer = hub.getWriter();
		String writerName = writer.getName();
		String writerType = (writer instanceof Individual) ? "개인" : "회사";

		return AcceptedCollaborationDto.builder()
			.collectionGatheringId(cg.getId())
			.firstImage(hub.getFirstImage())
			.postCreatedAt(hub.getCreatedAt())
			.postTitle(hub.getTitle())
			.postCategory("협업 광장 > " + hub.getSpecialization())
			.domain(recruitment.getDomain())
			.writerName(writerName)
			.writerType(writerType)
			.teamInfoList(teamInfoList)
			.build();
	}

	/**
	 * [C] 팀원 정보(TeamMemberInfoDto) 생성
	 *   - 게시글의 모든 CollaborationRecruitment 목록을 순회하며
	 *   - 각 도메인별 Gathering(totalQuantity)와 (accepted= true or initialGathering= true) 참가자들
	 */
	public List<TeamMemberInfoDto> createTeamInfoList(CollaborationHub hub) {
		List<CollaborationRecruitment> recruitments = hub.getCollaborations();

		return recruitments.stream()
			.map(this::toTeamMemberInfoDto)
			.collect(Collectors.toList());
	}

	private TeamMemberInfoDto toTeamMemberInfoDto(CollaborationRecruitment recruitment) {

		Gathering gathering = recruitment.getGathering();
		Long total = (gathering != null) ? gathering.getTotalQuantity() : 0L;
		Long occupied = (gathering != null) ? gathering.getOccupiedQuantity() : 0L;

		List<CollectionGathering> joined =
			findAcceptedOrInitial(recruitment.getCollectionGatherings());

		List<String> joinerIds = joined.stream()
			.map(cg -> cg.getJoiner().getName())
			.collect(Collectors.toList());

		return TeamMemberInfoDto.builder()
			.domain(recruitment.getDomain())
			.occupied(occupied)
			.total(total)
			.joinerIds(joinerIds)
			.build();
	}

	private List<CollectionGathering> findAcceptedOrInitial(List<CollectionGathering> cgs) {
		return cgs.stream()
			.filter(cg -> Boolean.TRUE.equals(cg.getAccepted())
				|| Boolean.TRUE.equals(cg.getInitialGathering()))
			.collect(Collectors.toList());
	}
}
