package com.brainpix.profile.dto.contactdto;

import com.brainpix.profile.entity.ContactType;
import java.util.List;
import lombok.Data;

@Data
public class ContactUpdateRequest {
    private List<ContactType> contactTypeList;
    private String contactValue;
}