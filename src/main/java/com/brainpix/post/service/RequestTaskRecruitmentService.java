package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
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
			if (recruitmentDto.getDomain() == null || recruitmentDto.getPriceDto() == null) {
				throw new BrainPixException(RecruitmentErrorCode.INVALID_REQUEST);
			}
		}

		for (RequestTaskRecruitmentDto recruitmentDto : recruitmentDtos) {
			// 가격 정보 생성
			Price price = priceService.createPrice(recruitmentDto.getPriceDto());

			RequestTaskRecruitment recruitment = createRequestTaskRecruitmentConverter.convertToRequestTaskRecruitment(
				requestTask, recruitmentDto, price);

			recruitments.add(recruitment);

		}
		recruitmentRepository.saveAll(recruitments);
	}

	/*
	@Transactional
	public void updateRecruitments(Long taskId, List<RequestTaskRecruitmentDto> recruitmentDtos) {
		if (recruitmentDtos == null || recruitmentDtos.isEmpty()) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER); //INVALID_REQUEST, "모집 분야는 최소 하나 이상 등록해야 합니다.");
		}

		for (RequestTaskRecruitmentDto recruitmentDto : recruitmentDtos) {
			if (recruitmentDto.getDomain() == null || recruitmentDto.getPriceDto() == null) {
				throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER); //INVALID_REQUEST, "도메인과 가격 정보를 정확히 입력해야 합니다.");
			}
		}

		// 기존 모집 데이터 가져오기
		List<RequestTaskRecruitment> existingRecruitments = recruitmentRepository.findByRequestTaskId(taskId);

		// 1. 기존 모집 리스트를 도메인 기준으로 매핑
		Map<String, RequestTaskRecruitment> existingRecruitmentMap = existingRecruitments.stream()
			.collect(Collectors.toMap(RequestTaskRecruitment::getDomain, Function.identity(), (existing, replacement) -> existing));

		// 2. 수정 및 추가 로직 처리
		for (RequestTaskRecruitmentDto recruitmentDto : recruitmentDtos) {
			RequestTaskRecruitment recruitment = existingRecruitmentMap.get(recruitmentDto.getDomain());

			if (recruitment != null) {
				// 기존 모집 정보 수정
				recruitment.updateRecruitmentFields(recruitmentDto.getDomain());
				// 가격 정보 업데이트는 PriceService를 통해 수행
				priceService.updatePrice(recruitment.getPrice(), recruitmentDto.getPriceDto());
				// 수정된 모집 항목은 맵에서 제거 (나머지는 삭제할 예정)
				existingRecruitmentMap.remove(recruitmentDto.getDomain());
			} else {
				// 새로운 모집 정보 추가
				RequestTaskRecruitment newRecruitment = RequestTaskRecruitment.builder()
					.domain(recruitmentDto.getDomain())
					.price(priceService.createPrice(recruitmentDto.getPriceDto()))
					.build();
				recruitmentRepository.save(newRecruitment);
			}
		}

		// 3. 삭제 로직 (기존 리스트에 없어진 항목은 제거)
		if (!existingRecruitmentMap.isEmpty()) {
			recruitmentRepository.deleteAll(existingRecruitmentMap.values());
		}
	}

	 */
}
