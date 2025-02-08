package com.brainpix.post.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.post.dto.GetCollaborationHubDetailDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.User;

public class GetCollaborationHubDetailDtoConverter {

	public static GetCollaborationHubDetailDto.Parameter toParameter(Long userId, Long collaborationId) {
		return GetCollaborationHubDetailDto.Parameter.builder()
			.userId(userId)
			.collaborationId(collaborationId)
			.build();
	}

	public static GetCollaborationHubDetailDto.Response toResponse(CollaborationHub collaborationHub,
		List<CollectionGathering> collectionGathering, User writer,
		Long saveCount,
		Long totalIdeas, Long totalCollaborations, Boolean isSavedPost, Boolean isMyPost) {

		// 작성자
		GetCollaborationHubDetailDto.Writer writerDto = toWriter(writer, totalIdeas, totalCollaborations);

		// 데드라인 계산
		LocalDateTime deadline = collaborationHub.getDeadline();
		LocalDateTime now = LocalDateTime.now();
		Long days = deadline.isBefore(now) ? 0L : ChronoUnit.DAYS.between(now, deadline);

		// 모집 단위 (개최 인원에 속하지 않는 모집 단위만 필터링)
		List<GetCollaborationHubDetailDto.Recruitment> recruitments = collaborationHub.getCollaborations().stream()
			.filter(recruitment ->
				collectionGathering.stream()
					.noneMatch(gathering -> gathering.getCollaborationRecruitment().equals(recruitment))
			)
			.map(GetCollaborationHubDetailDtoConverter::toRecruitment)
			.toList();

		// 개최 인원
		List<GetCollaborationHubDetailDto.OpenMember> openMembers = collectionGathering.stream()
			.map(GetCollaborationHubDetailDtoConverter::toOpenMember)
			.toList();

		return GetCollaborationHubDetailDto.Response.builder()
			.collaborationId(collaborationHub.getId())
			.thumbnailImageUrl(
				!collaborationHub.getImageList().isEmpty() ? collaborationHub.getImageList().get(0) : null)
			.category(collaborationHub.getSpecialization().toString())
			.auth(collaborationHub.getPostAuth().toString())
			.title(collaborationHub.getTitle())
			.content(collaborationHub.getContent())
			.link(collaborationHub.getLink())
			.deadline(days)
			.viewCount(collaborationHub.getViewCount())
			.saveCount(saveCount)
			.createdDate(collaborationHub.getCreatedAt().toLocalDate())
			.writer(writerDto)
			.attachments(collaborationHub.getAttachmentFileList())
			.recruitments(recruitments)
			.openMembers(openMembers)
			.openMyProfile(collaborationHub.getOpenMyProfile())
			.isSavedPost(isSavedPost)
			.isMyPost(isMyPost)
			.build();
	}

	public static GetCollaborationHubDetailDto.Writer toWriter(User writer, Long totalIdeas, Long totalCollaborations) {

		return GetCollaborationHubDetailDto.Writer.builder()
			.writerId(writer.getId())
			.name(writer.getName())
			.profileImageUrl(writer.getProfileImage())
			.role(writer.getUserType())
			.specialization(!writer.getProfile().getSpecializationList().isEmpty() ?
				writer.getProfile().getSpecializationList().get(0).toString() : null)
			.totalIdeas(totalIdeas)
			.totalCollaborations(totalCollaborations)
			.build();
	}

	public static GetCollaborationHubDetailDto.Recruitment toRecruitment(CollaborationRecruitment recruitment) {
		return GetCollaborationHubDetailDto.Recruitment.builder()
			.recruitmentId(recruitment.getId())
			.domain(recruitment.getDomain())
			.occupiedQuantity(recruitment.getGathering().getOccupiedQuantity())
			.totalQuantity(recruitment.getGathering().getTotalQuantity())
			.build();
	}

	public static GetCollaborationHubDetailDto.OpenMember toOpenMember(CollectionGathering collectionGathering) {
		return GetCollaborationHubDetailDto.OpenMember.builder()
			.userId(collectionGathering.getJoiner().getId())
			.name(collectionGathering.getJoiner().getName())
			.domain(collectionGathering.getCollaborationRecruitment().getDomain())
			.openMyProfile(collectionGathering.getOpenProfile())
			.build();
	}
}
