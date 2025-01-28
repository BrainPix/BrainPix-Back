package com.brainpix.post.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.RequestTaskErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.GetPopularRequestTaskListDtoConverter;
import com.brainpix.post.converter.GetRequestTaskDetailDtoConverter;
import com.brainpix.post.converter.GetRequestTaskListDtoConverter;
import com.brainpix.post.dto.GetPopularRequestTaskListDto;
import com.brainpix.post.dto.GetRequestTaskDetailDto;
import com.brainpix.post.dto.GetRequestTaskListDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestTaskQueryService {

	private final RequestTaskRepository requestTaskRepository;
	private final SavedPostRepository savedPostRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;

	// 요청 과제 메인페이지에서 검색 조건을 적용하여 요청 과제 목록을 반환합니다.
	public CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail> getRequestTaskList(
		GetRequestTaskListDto.Parameter parameter) {

		// 요청 과제-저장수 쌍으로 반환된 결과
		Page<Object[]> result = requestTaskRepository.findRequestTaskListWithSaveCount(parameter.getType(),
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(),
			parameter.getPageable());

		// dto로 변환
		Page<GetRequestTaskListDto.RequestTaskDetail> response = result
			.map(requestTask -> {
					RequestTask task = (RequestTask)requestTask[0];    // 실제 엔티티 객체
					Long saveCount = (Long)requestTask[1];        // 저장 횟수
					LocalDateTime deadline = task.getDeadline();    // 마감 기한
					LocalDateTime now = LocalDateTime.now();    // 현재 시간
					Long days = deadline.isBefore(now) ? 0L : ChronoUnit.DAYS.between(now, deadline); // D-DAY 계산
					return GetRequestTaskListDtoConverter.toRequestTaskDetail(task, saveCount, days);
				}
			);

		return CommonPageResponse.of(response);
	}

	// 저장순으로 요청 과제를 조회합니다.
	public CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> getPopularRequestTaskList(
		GetPopularRequestTaskListDto.Parameter parameter) {

		// 요청 과제-저장수 쌍으로 반환된 결과
		Page<Object[]> result = requestTaskRepository.findPopularRequestTaskListWithSaveCount(parameter.getType(),
			parameter.getPageable());

		// dto로 변환
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

	// 요청 과제 상세 페이지 내용을 조회합니다.
	public GetRequestTaskDetailDto.Response getRequestTaskDetail(
		GetRequestTaskDetailDto.Parameter parameter) {

		// 요청 과제 조회
		RequestTask requestTask = requestTaskRepository.findById(parameter.getTaskId())
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.TASK_NOT_FOUND));

		// 작성자 조회
		User writer = requestTask.getWriter();

		// 요청 과제 저장 횟수
		Long saveCount = savedPostRepository.countByPostId(parameter.getTaskId());

		// 작성자의 아이디어 개수
		Long totalIdeas = ideaMarketRepository.countByWriterId(writer.getId());

		// 작성자의 협업 횟수
		Long totalCollaborations = collectionGatheringRepository.countByJoinerIdAndAccepted(writer.getId(), true);

		return GetRequestTaskDetailDtoConverter.toResponse(requestTask, writer, saveCount, totalIdeas,
			totalCollaborations);
	}
}
