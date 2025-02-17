package com.brainpix.profile.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.dto.PublicProfileResponseDto;

@Component
public class ProfilePostConverter {
	/**
	 * 요청 과제 미리보기용 DTO 변환
	 */
	public PublicProfileResponseDto.PostPreviewDto toRequestTaskPreviewDto(RequestTask task, long savedCount) {
		String openScope = parseOpenScope(task.getPostAuth());

		return PublicProfileResponseDto.PostPreviewDto.builder()
			.postId(task.getId())
			.openScope(openScope)
			.specialization(task.getSpecialization())
			.title(task.getTitle())
			.writerName(task.getWriter().getNickName())
			.savedCount(savedCount)
			.viewCount(task.getViewCount())
			.deadline(task.getDeadline())
			.thumbnailImage(task.getFirstImage())
			.writerImageUrl(task.getWriter().getProfileImage())
			.build();
	}

	/**
	 * 아이디어 마켓 미리보기용 DTO 변환
	 */
	public PublicProfileResponseDto.PostPreviewDto toIdeaMarketPreviewDto(IdeaMarket market, long savedCount) {
		String openScope = parseOpenScope(market.getPostAuth());

		return PublicProfileResponseDto.PostPreviewDto.builder()
			.postId(market.getId())
			.openScope(openScope)
			.specialization(market.getSpecialization())
			.title(market.getTitle())
			.writerName(market.getWriter().getNickName())
			.savedCount(savedCount)
			.viewCount(market.getViewCount())
			.thumbnailImage(market.getFirstImage())
			.writerImageUrl(market.getWriter().getProfileImage())
			.price(market.getPrice().getPrice())
			.build();
	}

	/**
	 * 협업 광장 미리보기용 DTO 변환
	 */
	public PublicProfileResponseDto.PostPreviewDto toCollaborationHubPreviewDto(CollaborationHub hub, long savedCount) {
		long currentMembers = hub.getOccupiedQuantity();
		long totalMembers = hub.getTotalQuantity();
		String openScope = parseOpenScope(hub.getPostAuth());

		return PublicProfileResponseDto.PostPreviewDto.builder()
			.postId(hub.getId())
			.openScope(openScope)
			.specialization(hub.getSpecialization())
			.title(hub.getTitle())
			.writerName(hub.getWriter().getNickName())
			.savedCount(savedCount)
			.viewCount(hub.getViewCount())
			.deadline(hub.getDeadline())
			.thumbnailImage(hub.getFirstImage())
			.currentMembers(currentMembers)
			.totalMembers(totalMembers)
			.writerImageUrl(hub.getWriter().getProfileImage())
			.build();
	}

	private String parseOpenScope(PostAuth auth) {
		if (auth == PostAuth.COMPANY)
			return "기업 공개";
		if (auth == PostAuth.ME)
			return "개인 공개";
		return "전체 공개";
	}

}
