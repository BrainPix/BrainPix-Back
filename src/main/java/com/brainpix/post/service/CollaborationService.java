package com.brainpix.post.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.brainpix.joining.dto.ApplicantResponse;
import com.brainpix.joining.dto.RecruitmentApplicantsResponse;
import com.brainpix.post.dto.collaborationhub.CollaborationDetailResponse;
import com.brainpix.post.dto.collaborationhub.CollaborationPreviewResponse;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.collaborationhub.CollaborationHubRepository;
import com.brainpix.post.repository.collaborationhub.CollaborationRecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationService {

	private final CollaborationHubRepository hubRepository;
	private final CollaborationRecruitmentRepository recruitmentRepository;
	private final SavedPostRepository savedPostRepository;

	/**
	 * 협업광장 게시물 미리보기
	 */
	public List<CollaborationPreviewResponse> getCollaborationPreviews(Long userId) {
		// Fetch Join으로 모든 관련 데이터 로딩
		List<CollaborationHub> hubs = hubRepository.findAllByWriterIdFetchJoin(userId);

		return hubs.stream()
			.map(hub -> {
				Long saveCount = savedPostRepository.countByPostId(hub.getId());
				return CollaborationPreviewResponse.from(hub, saveCount);
			})
			.toList();
	}

	/**
	 * 협업광장 상세보기
	 * @param collaborationId 게시물 ID
	 * @return 협업광장 상세 정보
	 */
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
					.currentQuantity((long)applicantsByStatus.get(true).size())
					.acceptedApplicants(applicantsByStatus.get(true))
					.pendingApplicants(applicantsByStatus.get(false))
					.build();
			})
			.collect(Collectors.toList());

		return CollaborationDetailResponse.builder()
			.id(collaborationHub.getId())
			.title(collaborationHub.getTitle())
			.specialization(collaborationHub.getSpecialization())
			.imageUrl(
				collaborationHub.getImageList().isEmpty() ? null : collaborationHub.getImageList().get(0)
			)
			.deadline(collaborationHub.getDeadline())
			.link(collaborationHub.getLink())
			.applicants(recruitmentApplicants)
			.build();
	}

}
