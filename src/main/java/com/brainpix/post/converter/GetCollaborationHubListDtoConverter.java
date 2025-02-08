package com.brainpix.post.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetCollaborationHubListDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public class GetCollaborationHubListDtoConverter {
	public static GetCollaborationHubListDto.Parameter toParameter(Long userId,
		GetCollaborationHubListDto.Request request,
		Pageable pageable) {

		Specialization category = null;
		SortType sortType = null;

		try {
			category =
				request.getCategory() != null ? Specialization.valueOf(request.getCategory().toUpperCase()) : null;
			sortType = request.getSortType() != null ?
				SortType.valueOf("COLLABORATION_" + request.getSortType().toUpperCase()) : null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetCollaborationHubListDto.Parameter.builder()
			.userId(userId)
			.keyword(request.getKeyword())
			.category(category)
			.onlyCompany(request.getOnlyCompany())
			.sortType(sortType)
			.pageable(pageable)
			.build();
	}

	public static CommonPageResponse<GetCollaborationHubListDto.CollaborationDetail> toResponse(
		Page<Object[]> collaborationHubs) {

		Page<GetCollaborationHubListDto.CollaborationDetail> response = collaborationHubs
			.map(collaborationHub -> {
					CollaborationHub collaboration = (CollaborationHub)collaborationHub[0];    // 실제 엔티티 객체
					Long saveCount = (Long)collaborationHub[1];        // 저장 횟수
					LocalDateTime deadline = collaboration.getDeadline();    // 마감 기한
					LocalDateTime now = LocalDateTime.now();    // 현재 시간
					Long days = deadline.isBefore(now) ? 0L : ChronoUnit.DAYS.between(now, deadline); // D-DAY 계산

					// 현재 인원 및 모집 인원
					Long occupiedQuantity = collaboration.getOccupiedQuantity();
					Long totalQuantity = collaboration.getTotalQuantity();

					Boolean isSavedPost = (Boolean)collaborationHub[2];
					return toCollaborationDetail(collaboration, saveCount, days, occupiedQuantity, totalQuantity,
						isSavedPost);
				}
			);

		return CommonPageResponse.of(response);
	}

	public static GetCollaborationHubListDto.CollaborationDetail toCollaborationDetail(
		CollaborationHub collaborationHub, Long saveCount,
		Long deadline, Long occupiedQuantity, Long totalQuantity, Boolean isSavedPost) {
		return GetCollaborationHubListDto.CollaborationDetail.builder()
			.collaborationId(collaborationHub.getId())
			.auth(collaborationHub.getPostAuth().toString())
			.writerImageUrl(collaborationHub.getWriter().getProfileImage())
			.writerName(collaborationHub.getWriter().getName())
			.thumbnailImageUrl(
				!collaborationHub.getImageList().isEmpty() ? collaborationHub.getImageList().get(0) : null)
			.title(collaborationHub.getTitle())
			.deadline(deadline)
			.category(collaborationHub.getSpecialization().toString())
			.occupiedQuantity(occupiedQuantity)
			.totalQuantity(totalQuantity)
			.saveCount(saveCount)
			.viewCount(collaborationHub.getViewCount())
			.isSavedPost(isSavedPost)
			.build();
	}
}
