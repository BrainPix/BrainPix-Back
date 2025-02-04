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
	public static GetCollaborationHubListDto.Parameter toParameter(GetCollaborationHubListDto.Request request,
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
			.keyword(request.getKeyword())
			.category(category)
			.onlyCompany(request.getOnlyCompany())
			.sortType(sortType)
			.pageable(pageable)
			.build();
	}

	public static CommonPageResponse<GetCollaborationHubListDto.CollaborationDetail> toResponse(
		Page<Object[]> CollaborationHubs) {

		Page<GetCollaborationHubListDto.CollaborationDetail> response = CollaborationHubs
			.map(CollaborationHub -> {
					CollaborationHub collaboration = (CollaborationHub)CollaborationHub[0];    // 실제 엔티티 객체
					Long saveCount = (Long)CollaborationHub[1];        // 저장 횟수
					LocalDateTime deadline = collaboration.getDeadline();    // 마감 기한
					LocalDateTime now = LocalDateTime.now();    // 현재 시간
					Long days = deadline.isBefore(now) ? 0L : ChronoUnit.DAYS.between(now, deadline); // D-DAY 계산

					// 현재 인원 및 모집 인원
					Long occupiedQuantity = collaboration.getOccupiedQuantity();
					Long totalQuantity = collaboration.getTotalQuantity();
					return toCollaborationDetail(collaboration, saveCount, days, occupiedQuantity, totalQuantity);
				}
			);

		return CommonPageResponse.of(response);
	}

	public static GetCollaborationHubListDto.CollaborationDetail toCollaborationDetail(
		CollaborationHub CollaborationHub, Long saveCount,
		Long deadline, Long occupiedQuantity, Long totalQuantity) {
		return GetCollaborationHubListDto.CollaborationDetail.builder()
			.collaborationId(CollaborationHub.getId())
			.auth(CollaborationHub.getPostAuth().toString())
			.writerImageUrl(CollaborationHub.getWriter().getProfileImage())
			.writerName(CollaborationHub.getWriter().getName())
			.thumbnailImageUrl(CollaborationHub.getImageList().get(0))
			.title(CollaborationHub.getTitle())
			.deadline(deadline)
			.category(CollaborationHub.getSpecialization().toString())
			.occupiedQuantity(occupiedQuantity)
			.totalQuantity(totalQuantity)
			.saveCount(saveCount)
			.viewCount(CollaborationHub.getViewCount())
			.build();
	}
}
