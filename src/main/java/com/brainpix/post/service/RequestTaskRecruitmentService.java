package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.RecruitmentErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.service.PriceService;
import com.brainpix.post.converter.CreateRequestTaskRecruitmentConverter;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.repository.RequestTaskRecruitmentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTaskRecruitmentService {

	private final RequestTaskRecruitmentRepository recruitmentRepository;
	private final PriceService priceService;
	private final CreateRequestTaskRecruitmentConverter createRequestTaskRecruitmentConverter;

	@Transactional
	public void createRecruitments(RequestTask requestTask, List<RequestTaskRecruitmentDto> recruitmentDtos) {
		if (recruitmentDtos == null || recruitmentDtos.isEmpty()) {
			throw new BrainPixException(RecruitmentErrorCode.INVALID_INPUT);
		}

		List<RequestTaskRecruitment> recruitments = new ArrayList<>();

		for (RequestTaskRecruitmentDto recruitmentDto : recruitmentDtos) {
			if (recruitmentDto.getDomain() == null || recruitmentDto.getRequestTaskPriceDto() == null) {
				throw new BrainPixException(RecruitmentErrorCode.INVALID_REQUEST);
			}
		}

		for (RequestTaskRecruitmentDto recruitmentDto : recruitmentDtos) {
			// 가격 정보 생성
			Price price = priceService.createRequestTaskPrice(recruitmentDto.getRequestTaskPriceDto());

			RequestTaskRecruitment recruitment = createRequestTaskRecruitmentConverter.convertToRequestTaskRecruitment(
				requestTask, recruitmentDto, price);

			recruitments.add(recruitment);

		}
		recruitmentRepository.saveAll(recruitments);
	}
}
