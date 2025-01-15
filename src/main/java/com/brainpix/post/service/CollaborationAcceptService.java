package com.brainpix.post.service;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.joining.repository.GatheringRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollaborationAcceptService {
    private final CollectionGatheringRepository gatheringRepository;
    private final GatheringRepository gatheringEntityRepository;

    /**
     * 지원자 수락 로직
     * @param gatheringId 지원 ID
     */
    @Transactional
    public void acceptApplicant(Long gatheringId) {
        try {
            // 지원자 조회
            CollectionGathering collectionGathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

            if (Boolean.TRUE.equals(collectionGathering.getAccepted())) {
                throw new IllegalStateException("Applicant is already accepted");
            }

            // 모집 분야 상태 변경
            Gathering gatheringEntity = collectionGathering.getCollaborationRecruitment().getGathering();
            if (gatheringEntity.getOccupiedQuantity() >= gatheringEntity.getTotalQuantity()) {
                throw new IllegalStateException("No more spots available in this recruitment");
            }

            // 수락 처리
            collectionGathering.setAccepted(true);
            gatheringEntity.setOccupiedQuantity(gatheringEntity.getOccupiedQuantity() + 1);

            // 변경 사항 저장
            gatheringRepository.save(collectionGathering);
            gatheringEntityRepository.save(gatheringEntity);

        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Concurrent update detected. Please try again.", e);
        }
    }
    /**
     * 지원자 거절 로직
     * @param gatheringId 지원 ID
     */
    @Transactional
    public void rejectApplicant(Long gatheringId) {
        try {
            // 지원자 조회
            CollectionGathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

            if (Boolean.FALSE.equals(gathering.getAccepted())) {
                throw new IllegalStateException("Applicant is already rejected");
            }

            // 모집 분야 상태 변경
            Gathering gatheringEntity = gathering.getCollaborationRecruitment().getGathering();
            if (Boolean.TRUE.equals(gathering.getAccepted())) {
                gatheringEntity.setOccupiedQuantity(gatheringEntity.getOccupiedQuantity() - 1);
            }

            // 거절 처리
            gathering.setAccepted(false);

            // 저장
            gatheringRepository.save(gathering);
            gatheringEntityRepository.save(gatheringEntity);

        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Concurrent update detected. Please try again.", e);
        }
    }
}
