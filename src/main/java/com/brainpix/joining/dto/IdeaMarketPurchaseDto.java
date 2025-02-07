package com.brainpix.joining.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdeaMarketPurchaseDto {
	private Long purchasingId;
	@Schema(type = "string", example = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime purchasedAt;

	// 게시글 정보
	private Specialization specialization;
	private String title;
	private String writerName;
	private String writerType;    // "개인"/"회사"
	private Long middlePrice; //원가 *수량
	// 수량
	private Long quantity;

	// 결제금액 계산
	private Long fee;            // 수수료
	private Long finalPrice;     // (price × quantity) + fee
	private Long ideaMarketId;

}

