package com.brainpix.post.service;

import com.brainpix.joining.dto.ApplicantResponse;
import com.brainpix.joining.dto.RecruitmentApplicantsResponse;
import com.brainpix.post.dto.collaborationhub.CollaborationDetailResponse;
import com.brainpix.post.dto.collaborationhub.CollaborationPreviewResponse;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.post.repository.CollaborationRecruitmentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollaborationService {

    private final CollaborationHubRepository hubRepository;
    private final CollaborationRecruitmentRepository recruitmentRepository;


    /**
     * 협업광장 게시물 미리보기
     * @param userId 사용자 ID
     * @return 협업광장 게시물 미리보기 리스트
     */
    @Transactional(readOnly = true)
    public List<CollaborationPreviewResponse> getCollaborationPreviews(Long userId) {
        // ★ Fetch Join을 통해 한 번의 쿼리로 Hub + recruitments + gathering까지 전부 로딩
        List<CollaborationHub> hubs = hubRepository.findAllByWriterIdFetchJoin(userId);

        return hubs.stream()
            .map(hub -> {
                long daysLeft = hub.getDeadline().toLocalDate().toEpochDay() - LocalDate.now().toEpochDay();
                daysLeft = Math.max(daysLeft, 0);  // 0 이하가 되지 않도록 보정

                // recruitments는 이미 LAZY 필드지만, fetch join으로 한 번에 로딩됨
                List<CollaborationRecruitment> recruitments = hub.getRecruitments();

                // 한 번에 모집 인원, 현재 인원을 계산
                Long totalParticipants = recruitments.stream()
                    .mapToLong(r -> r.getGathering().getTotalQuantity())
                    .sum();
                Long currentParticipants = recruitments.stream()
                    .mapToLong(r -> r.getGathering().getOccupiedQuantity())
                    .sum();

                return CollaborationPreviewResponse.builder()
                    .id(hub.getId())
                    .nickname(hub.getWriter().getName())
                    .title(hub.getTitle())
                    .imageUrl(hub.getImageList().isEmpty() ? null : hub.getImageList().get(0))
                    .daysLeft(daysLeft)
                    .currentParticipants(currentParticipants)
                    .totalParticipants(totalParticipants)
                    .build();
            })
            .collect(Collectors.toList());
    }



    /**
     * 협업광장 상세보기
     * @param collaborationId 게시물 ID
     * @return 협업광장 상세 정보
     */
    @Transactional(readOnly = true)
    public CollaborationDetailResponse getCollaborationDetail(Long collaborationId) {
        // 이미 Hub 자체는 간단히 조회
        CollaborationHub collaborationHub = hubRepository.findById(collaborationId)
            .orElseThrow(() -> new IllegalArgumentException("CollaborationHub not found"));

        // ★ 한 번의 쿼리로 모집 + purchases + joiner까지 전부 로딩
        List<CollaborationRecruitment> recruitments
            = recruitmentRepository.findAllWithPurchasesByHubId(collaborationId);

        List<RecruitmentApplicantsResponse> recruitmentApplicants = recruitments.stream()
            .map(recruitment -> {
                Long totalQuantity = recruitment.getGathering().getTotalQuantity();

                // 이미 fetch join으로 purchases와 joiner가 로딩되었으므로 추가 쿼리 X
                Map<Boolean, List<ApplicantResponse>> applicantsByStatus = recruitment.getPurchases().stream()
                    .collect(Collectors.partitioningBy(
                        purchase -> Boolean.TRUE.equals(purchase.getAccepted()),
                        Collectors.mapping(
                            purchase -> ApplicantResponse.builder()
                                .applicantId(purchase.getJoiner().getId())
                                .applicantName(purchase.getJoiner().getName())
                                .accepted(purchase.getAccepted())
                                .build(),
                            Collectors.toList()
                        )
                    ));

                return RecruitmentApplicantsResponse.builder()
                    .recruitmentId(recruitment.getId())
                    .domain(recruitment.getDomain())
                    .totalQuantity(totalQuantity)
                    .currentQuantity((long) applicantsByStatus.get(true).size())
                    .acceptedApplicants(applicantsByStatus.get(true))
                    .pendingApplicants(applicantsByStatus.get(false))
                    .build();
            })
            .collect(Collectors.toList());

        return CollaborationDetailResponse.builder()
            .id(collaborationHub.getId())
            .title(collaborationHub.getTitle())
            .category(collaborationHub.getCategory())
            .imageUrl(
                collaborationHub.getImageList().isEmpty() ? null : collaborationHub.getImageList().get(0)
            )
            .deadline(collaborationHub.getDeadline())
            .link(collaborationHub.getLink())
            .collaborationType(collaborationHub.getCollaborationType())
            .applicants(recruitmentApplicants)
            .build();
    }

}
