package com.brainpix.post.converter;

import org.springframework.data.domain.Pageable;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetRequestTaskListDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public class GetRequestTaskListDtoConverter {

	public static GetRequestTaskListDto.Parameter toParameter(GetRequestTaskListDto.Request request,
		Pageable pageable) {

		RequestTaskType type = null;
		Specialization category = null;
		SortType sortType = null;

		try {
			type = request.getType() != null ? RequestTaskType.valueOf(request.getType().toUpperCase()) : null;
			category =
				request.getCategory() != null ? Specialization.valueOf(request.getCategory().toUpperCase()) : null;
			sortType =
				request.getSortType() != null ? SortType.valueOf("REQUEST_" + request.getSortType().toUpperCase()) :
					null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetRequestTaskListDto.Parameter.builder()
			.type(type)
			.keyword(request.getKeyword())
			.category(category)
			.onlyCompany(request.getOnlyCompany())
			.sortType(sortType)
			.pageable(pageable)
			.build();
	}

	public static GetRequestTaskListDto.RequestTaskDetail toRequestTaskDetail(RequestTask requestTask, Long saveCount,
		Long deadline) {
		return GetRequestTaskListDto.RequestTaskDetail.builder()
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
