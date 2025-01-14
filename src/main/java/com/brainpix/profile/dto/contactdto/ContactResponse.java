package com.brainpix.profile.dto.contactdto;

import com.brainpix.profile.entity.ContactType;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponse {
    private List<ContactType> contactTypeList;
    private String contactValue;
}