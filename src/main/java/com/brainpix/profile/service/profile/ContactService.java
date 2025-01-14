package com.brainpix.profile.service.profile;

import com.brainpix.profile.dto.contactdto.ContactResponse;
import com.brainpix.profile.dto.contactdto.ContactUpdateRequest;
import com.brainpix.profile.entity.Contact;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.repository.ContactRepository;
import com.brainpix.profile.repository.IndividualProfileRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final IndividualProfileRepository profileRepository;

    public ContactService(ContactRepository contactRepository,
        IndividualProfileRepository profileRepository) {
        this.contactRepository = contactRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> getContacts(Long userId) {
        // IndividualProfile에서 ContactOpen 값을 확인
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 공개 여부가 false라면 빈 리스트 반환
        if (!Boolean.TRUE.equals(profile.getContactOpen())) {
            return List.of(); // 연락처가 비공개인 경우
        }

        // 공개된 경우 Contact 데이터를 반환
        return contactRepository.findByIndividualProfileId(profile.getId()).stream()
            .map(contact -> ContactResponse.builder()
                .contactTypeList(contact.getContactTypeList())
                .contactValue(contact.getContactValue())
                .build())
            .collect(Collectors.toList());
    }


    @Transactional
    public void updateContacts(Long userId, List<ContactUpdateRequest> requests) {
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 기존 Contact 데이터 삭제
        contactRepository.deleteByIndividualProfileId(profile.getId());

        // 새로운 Contact 데이터 추가
        List<Contact> updatedContacts = requests.stream()
            .map(request -> Contact.builder()
                .contactTypeList(request.getContactTypeList())
                .contactValue(request.getContactValue())
                .individualProfile(profile)
                .build())
            .collect(Collectors.toList());

        contactRepository.saveAll(updatedContacts);

    }
}
