package com.brainpix.profile.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.dto.PublicProfileResponseDto;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.User;

@Component
public class ProfilePostConverter {
	/**
	 * 요청 과제 미리보기용 DTO 변환
	 */
	public PublicProfileResponseDto.PostPreviewDto toRequestTaskPreviewDto(RequestTask task, long savedCount) {
		String openScope = parseOpenScope(task.getPostAuth());
		String dDayString = task.getDeadline().toString();
		String writerName = getDisplayName(task.getWriter());

		return PublicProfileResponseDto.PostPreviewDto.builder()
			.postId(task.getId())
			.openScope(openScope)
			.categoryName("요청 과제 > " + task.getSpecialization())
			.title(task.getTitle())
			.writerName(writerName)
			.savedCount(savedCount)
			.viewCount(task.getViewCount())
			.deadline(dDayString)
			.thumbnailImage(task.getFirstImage())
			.writerImageUrl(task.getWriter().getProfileImage())
			.build();
	}

	/**
	 * 아이디어 마켓 미리보기용 DTO 변환
	 */
	public PublicProfileResponseDto.PostPreviewDto toIdeaMarketPreviewDto(IdeaMarket market, long savedCount) {
		String openScope = parseOpenScope(market.getPostAuth());
		String writerName = getDisplayName(market.getWriter());

		return PublicProfileResponseDto.PostPreviewDto.builder()
			.postId(market.getId())
			.openScope(openScope)
			.categoryName("아이디어 마켓 > " + market.getSpecialization())
			.title(market.getTitle())
			.writerName(writerName)
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
		long currentMembers = hub.getCollaborations().stream()
			.mapToLong(rec -> rec.getGathering().getOccupiedQuantity())
			.sum();
		long totalMembers = hub.getCollaborations().stream()
			.mapToLong(rec -> rec.getGathering().getTotalQuantity())
			.sum();
		String dDayString = hub.getDeadline().toString();
		String openScope = parseOpenScope(hub.getPostAuth());
		String writerName = getDisplayName(hub.getWriter());

		return PublicProfileResponseDto.PostPreviewDto.builder()
			.postId(hub.getId())
			.openScope(openScope)
			.categoryName("협업 광장 > " + hub.getSpecialization())
			.title(hub.getTitle())
			.writerName(writerName)
			.savedCount(savedCount)
			.viewCount(hub.getViewCount())
			.deadline(dDayString)
			.thumbnailImage(hub.getFirstImage())
			.currentMembers(currentMembers)
			.totalMembers(totalMembers)
			.writerImageUrl(hub.getWriter().getProfileImage())
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

}
