package com.brainpix.post.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetPopularRequestTaskListDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskType;

public class GetPopularRequestTaskListDtoConverter {

	public static GetPopularRequestTaskListDto.Parameter toParameter(GetPopularRequestTaskListDto.Request request,
		Pageable pageable) {

		RequestTaskType type = null;

		try {
			type = request.getType() != null ? RequestTaskType.valueOf(request.getType().toUpperCase()) : null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetPopularRequestTaskListDto.Parameter.builder()
			.type(type)
			.pageable(pageable)
			.build();
	}

	public static GetPopularRequestTaskListDto.RequestTaskDetail toRequestTaskDetail(RequestTask requestTask,
		Long saveCount,
		Long deadline) {
		return GetPopularRequestTaskListDto.RequestTaskDetail.builder()
			.taskId(requestTask.getId())
			.auth(requestTask.getPostAuth().toString())
			.writerImageUrl(requestTask.getWriter().getProfileImage())
			.writerName(requestTask.getWriter().getName())
			.thumbnailImageUrl(requestTask.getImageList().get(0))
			.title(requestTask.getTitle())
			.deadline(deadline)
			.category(requestTask.getSpecialization().toString())
			.saveCount(saveCount)
			.viewCount(requestTask.getViewCount())
			.build();
	}

	public static CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> toResponse(Page<Object[]> result) {

		Page<GetPopularRequestTaskListDto.RequestTaskDetail> response = result
			.map(requestTask -> {
					RequestTask task = (RequestTask)requestTask[0];    // 실제 엔티티 객체
					Long saveCount = (Long)requestTask[1];        // 저장 횟수
					LocalDateTime deadline = task.getDeadline();    // 마감 기한
					LocalDateTime now = LocalDateTime.now();    // 현재 시간
					Long days = deadline.isBefore(now) ? 0L : ChronoUnit.DAYS.between(now, deadline); // D-DAY 계산
					return GetPopularRequestTaskListDtoConverter.toRequestTaskDetail(task, saveCount, days);
				}
			);

		return CommonPageResponse.of(response);
	}
}
