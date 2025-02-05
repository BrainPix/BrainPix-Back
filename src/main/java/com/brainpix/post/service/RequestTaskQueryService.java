package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.converter.GetPopularRequestTaskListDtoConverter;
import com.brainpix.post.converter.GetRequestTaskDetailDtoConverter;
import com.brainpix.post.converter.GetRequestTaskListDtoConverter;
import com.brainpix.post.dto.GetPopularRequestTaskListDto;
import com.brainpix.post.dto.GetRequestTaskDetailDto;
import com.brainpix.post.dto.GetRequestTaskListDto;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.security.authority.BrainpixAuthority;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestTaskQueryService {

	private final RequestTaskRepository requestTaskRepository;
	private final SavedPostRepository savedPostRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;
	private final UserRepository userRepository;
	private final RequestTaskPurchasingRepository requestTaskPurchasingRepository;

	// 요청 과제 메인페이지에서 검색 조건을 적용하여 요청 과제 목록을 반환합니다.
	public CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail> getRequestTaskList(
		GetRequestTaskListDto.Parameter parameter) {

		// 요청 과제-저장수 쌍으로 반환된 결과
		Page<Object[]> result = requestTaskRepository.findRequestTaskListWithSaveCount(parameter.getType(),
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(),
			parameter.getPageable());

		// dto로 변환
		return GetRequestTaskListDtoConverter.toResponse(result);
	}

	// 저장순으로 요청 과제를 조회합니다.
	public CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> getPopularRequestTaskList(
		GetPopularRequestTaskListDto.Parameter parameter) {

		// 요청 과제-저장수 쌍으로 반환된 결과
		Page<Object[]> result = requestTaskRepository.findPopularRequestTaskListWithSaveCount(parameter.getType(),
			parameter.getPageable());

		// dto로 변환
		return GetPopularRequestTaskListDtoConverter.toResponse(result);
	}

	// 요청 과제 상세 페이지 내용을 조회합니다.
	public GetRequestTaskDetailDto.Response getRequestTaskDetail(
		GetRequestTaskDetailDto.Parameter parameter) {

		// 유저 조회
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		// 요청 과제 조회
		RequestTask requestTask = requestTaskRepository.findById(parameter.getTaskId())
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 개인이 기업 게시물을 상세보기 하는 경우 처리
		if (requestTask.getPostAuth().equals(PostAuth.COMPANY) && user.getAuthority()
			.equals(BrainpixAuthority.INDIVIDUAL)) {
			throw new BrainPixException(PostErrorCode.FORBIDDEN_ACCESS);
		}

		// 작성자 조회
		User writer = requestTask.getWriter();

		// 요청 과제 저장 횟수
		Long saveCount = savedPostRepository.countByPostId(parameter.getTaskId());

		// 작성자의 아이디어 개수
		Long totalIdeas = ideaMarketRepository.countByWriterId(writer.getId());

		// 작성자의 협업 횟수
		Long totalCollaborations = collectionGatheringRepository.countByJoinerIdAndAccepted(writer.getId(), true)
			+ collectionGatheringRepository.countByJoinerIdAndInitialGathering(
			writer.getId(), true) + requestTaskPurchasingRepository.countByBuyerIdAndAccepted(writer.getId(), true);

		return GetRequestTaskDetailDtoConverter.toResponse(requestTask, writer, saveCount, totalIdeas,
			totalCollaborations);
	}
}
