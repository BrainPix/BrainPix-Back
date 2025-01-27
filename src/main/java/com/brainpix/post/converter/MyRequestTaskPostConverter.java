package com.brainpix.post.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.MyRequestTaskPostDetailDto;
import com.brainpix.post.dto.MyRequestTaskPostDto;
import com.brainpix.post.dto.RequestTaskCurrentMember;
import com.brainpix.post.dto.RequestTaskSupportInfo;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.User;

@Component
public class MyRequestTaskPostConverter {

	/**
	 * 목록용 DTO 변환
	 */
	public MyRequestTaskPostDto toMyRequestTaskPostDto(RequestTask task, long savedCount) {
		String writerName = getDisplayName(task.getWriter());
		String openScope = parseOpenScope(task.getPostAuth());
		String dDayString = calcDDay(task.getDeadline());

		return MyRequestTaskPostDto.builder()
			.postId(task.getId())
			.openScope(openScope)
			.categoryName("요청 과제 > " + task.getSpecialization())
			.title(task.getTitle())
			.writerName(writerName)
			.savedCount(savedCount)
			.viewCount(task.getViewCount())
			.dDay(dDayString)
			.thumbnailImage(task.getFirstImage())
			.build();
	}

	/**
	 * 상세 조회용 DTO 변환
	 */
	public MyRequestTaskPostDetailDto toDetailDto(RequestTask task, List<RequestTaskSupportInfo> supportList,
		List<RequestTaskCurrentMember> currentMembers) {
		return MyRequestTaskPostDetailDto.builder()
			.postId(task.getId())
			.title(task.getTitle())
			.category("요청 과제 > " + task.getSpecialization())
			.dDay(calcDDay(task.getDeadline()))
			.thumbnailImage(task.getFirstImage())
			.supportStatus(supportList)
			.currentMembers(currentMembers)
			.build();
	}

	/**
	 * 지원 현황 변환
	 */
	public RequestTaskSupportInfo toSupportStatusDto(User user, String domain, Long occupiedQuantity,
		Long totalQuantity) {
		return RequestTaskSupportInfo.builder()
			.userId(user.getIdentifier())
			.role(domain)
			.currentSlashTotal(occupiedQuantity + " / " + totalQuantity)
			.build();
	}

	/**
	 * 현재 인원 변환
	 */
	public RequestTaskCurrentMember toCurrentMemberDto(User user, String domain, Long occupiedQuantity) {
		return RequestTaskCurrentMember.builder()
			.userId(user.getIdentifier())
			.role(domain)
			.memberCount(occupiedQuantity)
			.build();
	}

	private String getDisplayName(User user) {
		if (user instanceof Company) {
			return user.getName();  // 기업명
		} else {
			return user.getNickName(); // 개인 닉네임
		}
	}

	private String parseOpenScope(PostAuth auth) {
		if (auth == PostAuth.COMPANY)
			return "기업 공개";
		if (auth == PostAuth.ME)
			return "개인 공개";
		return "전체 공개";
	}

	private String calcDDay(LocalDateTime deadline) {
		if (deadline == null)
			return "D-Day 없음";
		long diff = ChronoUnit.DAYS.between(LocalDateTime.now(), deadline);
		if (diff < 0)
			return "모집 마감 (D+" + Math.abs(diff) + ")";
		return "모집 마감 (D-" + diff + ")";
	}
}