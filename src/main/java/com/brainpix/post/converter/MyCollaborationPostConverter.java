package com.brainpix.post.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.mypostdto.CollaborationCurrentMemberInfo;
import com.brainpix.post.dto.mypostdto.CollaborationSupportInfo;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubDetailDto;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubListDto;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.user.entity.User;

@Component
public class MyCollaborationPostConverter {

	public MyCollaborationHubListDto toCollaborationHubListDto(CollaborationHub hub, long savedCount,
		long currentMembers,
		long totalMembers) {
		String openScope = parseOpenScope(hub.getPostAuth());
		String dDay = calcDDay(hub.getDeadline());

		return MyCollaborationHubListDto.builder()
			.postId(hub.getId())
			.title(hub.getTitle())
			.openScope(openScope)
			.categoryName("협업 광장 > " + hub.getSpecialization())
			.writerName(hub.getWriter().getNickName())
			.savedCount(savedCount)
			.viewCount(hub.getViewCount())
			.dDay(dDay)
			.currentMembers(currentMembers)
			.totalMembers(totalMembers)
			.thumbnailImage(hub.getFirstImage())
			.build();
	}

	public MyCollaborationHubDetailDto toCollaborationHubDetailDto(CollaborationHub hub,
		List<CollaborationSupportInfo> supportInfos,
		List<CollaborationCurrentMemberInfo> currentMembers) {
		String dDay = calcDDay(hub.getDeadline());

		return MyCollaborationHubDetailDto.builder()
			.postId(hub.getId())
			.title(hub.getTitle())
			.category("협업 광장 > " + hub.getSpecialization())
			.dDay(dDay)
			.thumbnailImage(hub.getFirstImage())
			.link(hub.getLink())
			.supportStatus(supportInfos)
			.currentMembers(currentMembers)
			.build();
	}

	public CollaborationSupportInfo toSupportInfo(User user, String role, long currentQuantity, long totalQuantity) {
		return CollaborationSupportInfo.builder()
			.userId(user.getIdentifier())
			.role(role)
			.currentSlashTotal(currentQuantity + " / " + totalQuantity)
			.build();
	}

	public CollaborationCurrentMemberInfo toCurrentMemberInfo(User user, String role, long memberCount) {
		return CollaborationCurrentMemberInfo.builder()
			.userId(user.getIdentifier())
			.role(role)
			.memberCount(memberCount)
			.build();
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
		long days = ChronoUnit.DAYS.between(LocalDateTime.now(), deadline);
		return days >= 0 ? "D-" + days : "D+" + Math.abs(days);
	}
}
