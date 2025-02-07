package com.brainpix.post.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.brainpix.post.dto.GetRequestTaskDetailDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.user.entity.User;

public class GetRequestTaskDetailDtoConverter {

	public static GetRequestTaskDetailDto.Parameter toParameter(Long taskId, Long userId) {
		return GetRequestTaskDetailDto.Parameter.builder()
			.taskId(taskId)
			.userId(userId)
			.build();
	}

	public static GetRequestTaskDetailDto.Response toResponse(RequestTask requestTask, User writer, Long saveCount,
		Long totalIdeas, Long totalCollaborations) {

		// 작성자
		GetRequestTaskDetailDto.Writer writerDto = toWriter(writer, totalIdeas, totalCollaborations);

		// 모집 단위
		List<GetRequestTaskDetailDto.Recruitment> recruitments = requestTask.getRecruitments().stream()
			.map(GetRequestTaskDetailDtoConverter::toRecruitment)
			.toList();

		// 데드라인 계산
		LocalDateTime deadline = requestTask.getDeadline();
		LocalDateTime now = LocalDateTime.now();
		Long days = deadline.isBefore(now) ? 0L : ChronoUnit.DAYS.between(now, deadline);

		return GetRequestTaskDetailDto.Response.builder()
			.taskId(requestTask.getId())
			.thumbnailImageUrl(requestTask.getImageList() != null ? requestTask.getImageList().get(0) : null)
			.category(requestTask.getSpecialization().toString())
			.requestTaskType(requestTask.getRequestTaskType().toString())
			.auth(requestTask.getPostAuth().toString())
			.title(requestTask.getTitle())
			.content(requestTask.getContent())
			.deadline(days)
			.viewCount(requestTask.getViewCount())
			.saveCount(saveCount)
			.createdDate(requestTask.getCreatedAt().toLocalDate())
			.writer(writerDto)
			.attachments(requestTask.getAttachmentFileList())
			.recruitments(recruitments)
			.openMyProfile(requestTask.getOpenMyProfile())
			.build();
	}

	public static GetRequestTaskDetailDto.Writer toWriter(User writer, Long totalIdeas, Long totalCollaborations) {
		return GetRequestTaskDetailDto.Writer.builder()
			.writerId(writer.getId())
			.name(writer.getName())
			.profileImageUrl(writer.getProfileImage())
			.role(writer.getUserType())
			.specialization(!writer.getProfile().getSpecializationList().isEmpty() ?
				writer.getProfile().getSpecializationList().get(0).toString() : null)
			.totalIdeas(totalIdeas)
			.totalCollaborations(totalCollaborations)
			.build();
	}

	public static GetRequestTaskDetailDto.Recruitment toRecruitment(RequestTaskRecruitment recruitment) {

		return GetRequestTaskDetailDto.Recruitment.builder()
			.recruitmentId(recruitment.getId())
			.domain(recruitment.getDomain())
			.occupiedQuantity(recruitment.getPrice().getOccupiedQuantity())
			.totalQuantity(recruitment.getPrice().getTotalQuantity())
			.price(recruitment.getPrice().getPrice())
			.paymentDuration(recruitment.getPrice().getPaymentDuration().toString())
			.build();
	}
}
