package com.brainpix.joining.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.AcceptedRequestTaskPurchasingDto;
import com.brainpix.joining.dto.RejectedRequestTaskPurchasingDto;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;

@Component
public class RequestTaskPurchasingConverter {

	/**
	 * 거절된 지원 목록용 DTO 변환
	 */
	public RejectedRequestTaskPurchasingDto toRejectedDto(RequestTaskPurchasing p) {

		RequestTaskRecruitment recruitment = p.getRequestTaskRecruitment();

		RequestTask requestTask = recruitment.getRequestTask();

		return RejectedRequestTaskPurchasingDto.builder()
			.purchasingId(p.getId())
			.firstImage(requestTask.getFirstImage())
			.postCreatedAt(requestTask.getCreatedAt())
			.postTitle(requestTask.getTitle())
			.specialization(requestTask.getSpecialization())
			.domain(recruitment.getDomain())
			.build();
	}

	/**
	 * 수락된 지원 목록용 DTO 변환
	 */
	public AcceptedRequestTaskPurchasingDto toAcceptedDto(RequestTaskPurchasing p) {
		RequestTaskRecruitment recruitment = p.getRequestTaskRecruitment();
		RequestTask requestTask = recruitment.getRequestTask();

		User writer = requestTask.getWriter();
		String writerName = writer.getName();
		String writerType = (writer instanceof Individual) ? "개인" : "회사";

		return AcceptedRequestTaskPurchasingDto.builder()
			.purchasingId(p.getId())
			.firstImage(requestTask.getFirstImage())
			.postCreatedAt(requestTask.getCreatedAt())
			.postTitle(requestTask.getTitle())
			.specialization(requestTask.getSpecialization())
			.domain(recruitment.getDomain())
			.writerName(writerName)
			.writerType(writerType)
			.build();
	}
}
