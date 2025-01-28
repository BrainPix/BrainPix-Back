package com.brainpix.post.converter;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.post.dto.mypostdto.IdeaMarketPurchaseInfo;
import com.brainpix.post.dto.mypostdto.MyIdeaMarketPostDetailDto;
import com.brainpix.post.dto.mypostdto.MyIdeaMarketPostDto;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;

@Component
public class MyIdeaMarketPostConverter {

	// [목록용 DTO]
	public MyIdeaMarketPostDto toMyIdeaMarketPostDto(IdeaMarket ideaMarket, long savedCount) {
		User writer = ideaMarket.getWriter();

		String openScope = UserDisplayNameUtil.parseOpenScope(ideaMarket.getPostAuth());
		String displayName = UserDisplayNameUtil.getDisplayName(writer);

		String category = "아이디어마켓 > " + ideaMarket.getSpecialization();
		Long priceVal = (ideaMarket.getPrice() != null)
			? ideaMarket.getPrice().getPrice() : 0L;

		return MyIdeaMarketPostDto.builder()
			.postId(ideaMarket.getId())
			.openScope(openScope)
			.displayName(displayName)
			.categoryName(category)
			.title(ideaMarket.getTitle())
			.price(priceVal)
			.savedCount(savedCount)
			.viewCount(ideaMarket.getViewCount())
			.thumbnailImage(ideaMarket.getFirstImage()) // or "thumbnail does not exist;"
			.build();

	}

	// [구매자 정보 서브 DTO]
	public IdeaMarketPurchaseInfo toPurchaseInfo(IdeaMarketPurchasing purchasing) {
		User buyer = purchasing.getBuyer();

		// 결제방법은 여기서 임의로 "카카오페이" 등 하드코딩하거나, paymentDuration 를 변환
		String paymentMethod = (purchasing.getPaymentDuration() != null)
			? purchasing.getPaymentDuration().name()
			: "카카오페이";
		Long paidAmount = purchasing.getPrice();

		return IdeaMarketPurchaseInfo.builder()
			.buyerId(buyer.getIdentifier()) // or buyer.getName()
			.paymentMethod(paymentMethod)
			.paidAmount(paidAmount)
			.build();
	}

	// [상세 페이지 DTO]
	public MyIdeaMarketPostDetailDto toDetailDto(IdeaMarket ideaMarket, List<IdeaMarketPurchaseInfo> purchaseList) {
		User writer = ideaMarket.getWriter();
		String displayName = UserDisplayNameUtil.getDisplayName(writer);

		List<String> images = ideaMarket.getImageList();
		if (images == null) {
			images = Collections.emptyList();
		}

		String category = "아이디어마켓 > " + ideaMarket.getSpecialization();
		Long priceVal = (ideaMarket.getPrice() != null)
			? ideaMarket.getPrice().getPrice()
			: 0L;

		return MyIdeaMarketPostDetailDto.builder()
			.postId(ideaMarket.getId())
			.title(ideaMarket.getTitle())
			.price(priceVal)
			.categoryName(category)
			.displayName(displayName)
			.images(images)
			.purchaseList(purchaseList)
			.build();
	}

	public static class UserDisplayNameUtil {

		// 기업/개인 으로 구분
		private static String parseOpenScope(PostAuth auth) {
			if (auth == PostAuth.COMPANY)
				return "기업 공개";
			if (auth == PostAuth.ME)
				return "개인 공개";
			return "전체 공개";
		}

		// 작성자(User) 엔티티를 받아서, "개인이면 nickName, 기업이면 name"을 반환했어요,,
		public static String getDisplayName(User writer) {
			if (writer instanceof Company) {
				// 기업 사용자라면 Company.name
				return writer.getName();
			} else if (writer instanceof Individual) {
				// 개인 사용자라면 Individual.nickName
				return writer.getNickName();
			} else {
				throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
			}
		}
	}

}

