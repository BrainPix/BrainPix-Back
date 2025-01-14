package com.brainpix.profile.dto.individualinformationdto;

import lombok.Data;

@Data
public class IndividualProfileUpdateRequest {
    private String selfIntroduction;
    private Boolean contactOpen;
    private Boolean careerOpen;
    private Boolean stackOpen;
}
