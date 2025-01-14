package com.brainpix.profile.dto.individualinformationdto;

import com.brainpix.profile.dto.careerdto.CareerResponse;
import com.brainpix.profile.dto.contactdto.ContactResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioListResponse;
import com.brainpix.profile.dto.stackdto.StackResponse;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InformationPageResponse {
    private IndividualProfileResponse profile;
    private List<StackResponse> stacks;
    private List<PortfolioListResponse> portfolios;
    private List<CareerResponse> careers;
    private List<ContactResponse> contacts;
}