package com.brainpix.profile.entity;

public enum Specialization {
	ADVERTISING_PROMOTION, // 광고 · 홍보
	DESIGN,               // 디자인
	LESSON,               // 레슨
	MARKETING,            // 마케팅
	DOCUMENT_WRITING,     // 문서 · 글쓰기
	MEDIA_CONTENT,        // 미디어 · 콘텐츠
	TRANSLATION_INTERPRETATION, // 번역 및 통역
	TAX_LAW_LABOR,        // 세무 · 법무 · 노무
	CUSTOM_PRODUCTION,    // 주문제작
	STARTUP_BUSINESS,     // 창업 · 사업
	FOOD_BEVERAGE,        // 푸드 및 음료
	IT_TECH,              // IT · 테크
	OTHERS;                    //기타

	public static Specialization of(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Specialization cannot be null.");
		}
		try {
			// valueOf로 매칭된 enum 반환
			return Specialization.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			// 그 외 값은 예외 발생
			throw new IllegalArgumentException("Invalid Specialization: " + name);
		}
	}
}
