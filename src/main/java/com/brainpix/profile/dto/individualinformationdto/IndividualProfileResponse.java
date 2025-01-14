package com.brainpix.profile.dto.individualinformationdto;

import com.brainpix.profile.entity.Specialization;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndividualProfileResponse {
    private String selfIntroduction;
    private Boolean contactOpen;
    private Boolean careerOpen;
    private Boolean stackOpen;
    private List<Specialization> specializations;
}
