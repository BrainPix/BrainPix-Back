package com.brainpix.profile.dto.individualinformationdto;

import com.brainpix.profile.dto.TopInfoResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InformationCompleteResponse {
    private TopInfoResponse topInfo; // 상단 정보
    private InformationPageResponse middleContent; // 중단 내용 (마이페이지 데이터)
}
