package com.brainpix.profile.dto.individualinformationdto;

import com.brainpix.profile.dto.careerdto.CareerUpdateRequest;
import com.brainpix.profile.dto.contactdto.ContactUpdateRequest;
import com.brainpix.profile.dto.portfoliodto.PortfolioUpdateRequest;
import com.brainpix.profile.dto.stackdto.StackUpdateRequest;
import java.util.List;
import lombok.Data;

@Data
public class InformationUpdateRequest {
    private IndividualProfileUpdateRequest profile; // 프로필 수정 요청
    private List<StackUpdateRequest> stacks;        // 스택 수정 요청
    private List<PortfolioUpdateRequest> portfolios; // 포트폴리오 수정 요청
    private List<CareerUpdateRequest> careers;      // 경력 수정 요청
    private List<ContactUpdateRequest> contacts;    // 연락처 수정 요청
}
