package com.brainpix.post.converter;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.post.dto.ApplyRequestTaskDto;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.user.entity.User;

public class ApplyRequestTaskDtoConverter {

	public static ApplyRequestTaskDto.Parameter toParameter(Long taskId, Long userId,
		ApplyRequestTaskDto.Request request) {
		return ApplyRequestTaskDto.Parameter.builder()
			.taskId(taskId)
			.userId(userId)
			.requestRecruitmentId(request.getRequestRecruitmentId())
			.isOpenProfile(request.getIsOpenProfile())
			.message(request.getMessage())
			.build();
	}

	public static RequestTaskPurchasing toRequestTaskPurchasing(User user, RequestTaskRecruitment recruitment,
		Boolean openProfile, String message) {
		return RequestTaskPurchasing.builder()
			.buyer(user)
			.price(recruitment.getPrice().getPrice())
			.paymentDuration(recruitment.getPrice().getPaymentDuration())
			.accepted(null)    // 보류 상태
			.openProfile(openProfile)
			.message(message)
			.build();
	}
}
