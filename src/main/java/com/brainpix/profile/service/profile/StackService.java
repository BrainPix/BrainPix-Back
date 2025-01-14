package com.brainpix.profile.service.profile;

import com.brainpix.profile.dto.stackdto.StackResponse;
import com.brainpix.profile.dto.stackdto.StackUpdateRequest;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.entity.Stack;
import com.brainpix.profile.repository.IndividualProfileRepository;
import com.brainpix.profile.repository.StackRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StackService {

    private final StackRepository stackRepository;
    private final IndividualProfileRepository profileRepository;

    public StackService(StackRepository stackRepository, IndividualProfileRepository profileRepository) {
        this.stackRepository = stackRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional(readOnly = true)
    public List<StackResponse> getStacks(Long userId) {
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 공개 여부 체크
        if (!profile.getStackOpen()) {
            return List.of(); // 비공개인 경우 빈 리스트 반환
        }

        return stackRepository.findByUserId(userId).stream()
            .map(stack -> StackResponse.builder()
                .stackName(stack.getStackName())
                .proficiency(stack.getStackProficiency())
                .build())
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateStacks(Long userId, List<StackUpdateRequest> requests) {
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!Boolean.TRUE.equals(profile.getStackOpen())) {
            throw new IllegalStateException("Stack is not open for updates");
        }

        stackRepository.deleteByIndividualProfileId(profile.getId());

        List<Stack> stacks = requests.stream()
            .map(request -> Stack.builder()
                .stackName(request.getStackName())
                .stackProficiency(request.getProficiency())
                .individualProfile(profile)
                .build())
            .collect(Collectors.toList());

        stackRepository.saveAll(stacks);
    }
}
