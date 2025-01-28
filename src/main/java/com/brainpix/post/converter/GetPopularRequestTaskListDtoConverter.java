package com.brainpix.post.converter;

import org.springframework.data.domain.Pageable;

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
}
