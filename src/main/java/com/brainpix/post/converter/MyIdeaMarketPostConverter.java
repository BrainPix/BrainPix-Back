package com.brainpix.post.converter;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.post.dto.IdeaMarketPurchaseInfo;
import com.brainpix.post.dto.MyIdeaMarketPostDetailDto;
import com.brainpix.post.dto.MyIdeaMarketPostDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;

@Component
public class MyIdeaMarketPostConverter {

	// [목록용 DTO]
	public MyIdeaMarketPostDto toMyIdeaMarketPostDto(IdeaMarket ideaMarket, long savedCount) {
		User writer = ideaMarket.getWriter();

		String writerType = UserDisplayNameUtil.getWriterType(writer);
		String displayName = UserDisplayNameUtil.getDisplayName(writer);

		String category = "아이디어마켓 > " + ideaMarket.getSpecialization();
		Long priceVal = (ideaMarket.getPrice() != null)
			? ideaMarket.getPrice().getPrice() : 0L;

		return MyIdeaMarketPostDto.builder()
			.postId(ideaMarket.getId())
			.writerType(writerType)
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
		String writerType = UserDisplayNameUtil.getWriterType(writer);
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
			.writerType(writerType)
			.displayName(displayName)
			.images(images)
			.purchaseList(purchaseList)
			.build();
	}

	public static class UserDisplayNameUtil {

		// "기업/개인" 구분
		public static String getWriterType(User user) {
			if (user instanceof Company) {
				return "기업";
			} else if (user instanceof Individual) {
				return "개인";
			} else {
				return "개인"; // 혹은 “기타” 등
			}
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
				// 혹시 예외적인 경우가 있다면, 기본 name 사용
				return writer.getName();
			}
		}
	}

}

