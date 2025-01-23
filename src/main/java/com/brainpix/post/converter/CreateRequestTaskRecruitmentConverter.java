package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;

@Component
public class CreateRequestTaskRecruitmentConverter {

	public RequestTaskRecruitment convertToRequestTaskRecruitment(RequestTask requestTask, RequestTaskRecruitmentDto recruitmentDto, Price price) {
		return RequestTaskRecruitment.builder()
			.requestTask(requestTask)
			.domain(recruitmentDto.getDomain())
			.price(price)
			.build();
	}
}
