package com.brainpix.post.dto.mypostdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyIdeaMarketPostDto {

	private Long postId;            // 아이디어마켓 PK
	private String openScope;      // 공개 범위(기업 공개/개인 공개/전체 공개)
	private String categoryName;    // ex) "아이디어 마켓 > 디자인"
	private String title;           // 게시글 제목
	private String displayName;      // (개인이면 nickName, 기업이면 name)
	private Long price;             // 아이템 단가 (IdeaMarket.price.getPrice())
	private Long savedCount;        // 저장(스크랩) 횟수
	private Long viewCount;         // 조회수 (Post.viewCount)
	private String thumbnailImage;  // 첫 번째 이미지(없으면 "thumbnail does not exist;")
}
