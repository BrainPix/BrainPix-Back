package com.brainpix.post.dto.mypostdto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyIdeaMarketPostDetailDto {

	private Long postId;
	private String title;
	private Long price;           // 단가
	private String categoryName;  // "아이디어마켓 > 디자인"
	private List<String> images;  // 게시물 imageList
	private String displayName;   // (개인이면 nickName, 기업이면 name)

	// 구매 현황
	private List<IdeaMarketPurchaseInfo> purchaseList;
}

