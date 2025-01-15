package com.brainpix.post.service;

import com.brainpix.joining.dto.ApplicantResponse;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.PriceRepository;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestTaskAcceptService {
    private final RequestTaskPurchasingRepository purchasingRepository;
    private final PriceRepository priceRepository;

    /**
     * 지원자 수락 로직
     * @param purchaseId 지원 ID
     */
    @Transactional
    public void acceptApplicant(Long purchaseId) {
        try {
            RequestTaskPurchasing purchasing = purchasingRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

            if (Boolean.TRUE.equals(purchasing.getAccepted())) {
                throw new IllegalStateException("Applicant is already accepted");
            }

            Price price = purchasing.getRequestTaskRecruitment().getPrice();
            if (price.getOccupiedQuantity() >= price.getTotalQuantity()) {
                throw new IllegalStateException("No more spots available in this recruitment");
            }

            // 수락 처리
            purchasing.setAccepted(true);
            price.setOccupiedQuantity(price.getOccupiedQuantity() + 1);

            // 저장
            purchasingRepository.save(purchasing);
            priceRepository.save(price);

        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Concurrent update detected. Please try again.", e);
        }
    }

    @Transactional
    public void rejectApplicant(Long purchaseId) {
        try {
            RequestTaskPurchasing purchasing = purchasingRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

            if (Boolean.FALSE.equals(purchasing.getAccepted())) {
                throw new IllegalStateException("Applicant is already rejected");
            }

            Price price = purchasing.getRequestTaskRecruitment().getPrice();
            if (Boolean.TRUE.equals(purchasing.getAccepted())) {
                price.setOccupiedQuantity(price.getOccupiedQuantity() - 1);
            }

            // 거절 처리
            purchasing.setAccepted(false);

            // 저장
            purchasingRepository.save(purchasing);
            priceRepository.save(price);

        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Concurrent update detected. Please try again.", e);
        }
    }
}
