package com.brainpix.post.service;

import com.brainpix.joining.dto.ApplicantResponse;
import com.brainpix.joining.dto.RecruitmentApplicantsResponse;
import com.brainpix.post.dto.requesttask.RequestTaskDetailResponse;
import com.brainpix.post.dto.requesttask.RequestTaskPreviewResponse;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.RequestTaskRecruitmentRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestTaskService {

    private final RequestTaskRepository requestTaskRepository;
    private final RequestTaskRecruitmentRepository recruitmentRepository;



    /**
     * 본인 게시물 - 요청과제 미리보기
     * @param userId 사용자 ID
     * @return 요청과제 게시물 리스트
     */
    @Transactional(readOnly = true)
    public List<RequestTaskPreviewResponse> getMyRequestTasks(Long userId) {
        return requestTaskRepository.findByWriterId(userId).stream()
            .map(task -> {
                long daysLeft = task.getDeadline().toLocalDate().toEpochDay() - LocalDate.now().toEpochDay();

                return RequestTaskPreviewResponse.builder()
                    .id(task.getId())
                    .nickname(task.getWriter().getName())
                    .profileImage(task.getWriter().getProfileImage())
                    .title(task.getTitle())
                    .imageUrl(task.getImageList().isEmpty() ? null : task.getImageList().get(0))
                    .daysLeft(daysLeft > 0 ? daysLeft : 0) // 남은 날짜 계산
                    .build();
            })
            .collect(Collectors.toList());
    }

    /**
     * 본인 게시물 - 요청과제 상세보기
     * @param taskId 게시물 ID
     * @return 요청과제 게시물 상세 정보
     */
    @Transactional(readOnly = true)
    public RequestTaskDetailResponse getRequestTaskDetail(Long taskId) {
        // 요청 과제 조회
        RequestTask task = requestTaskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        // 모집 및 지원자 조회
        List<RecruitmentApplicantsResponse> recruitmentApplicants = recruitmentRepository.findByRequestTaskId(taskId).stream()
            .map(recruitment -> {
                Long totalQuantity = recruitment.getPrice().getTotalQuantity();

                // 지원자 분류
                Map<Boolean, List<ApplicantResponse>> applicantsByStatus = recruitment.getPurchases().stream()
                    .collect(Collectors.partitioningBy(
                        purchase -> Boolean.TRUE.equals(purchase.getAccepted()),
                        Collectors.mapping(
                            purchase -> ApplicantResponse.builder()
                                .applicantId(purchase.getBuyer().getId())
                                .applicantName(purchase.getBuyer().getName())
                                .build(),
                            Collectors.toList()
                        )
                    ));

                return RecruitmentApplicantsResponse.builder()
                    .recruitmentId(recruitment.getId())
                    .domain(recruitment.getDomain())
                    .totalQuantity(totalQuantity)
                    .currentQuantity((long) applicantsByStatus.get(true).size()) // 수락된 지원자 수
                    .acceptedApplicants(applicantsByStatus.get(true))
                    .pendingApplicants(applicantsByStatus.get(false))
                    .build();
            })
            .collect(Collectors.toList());

        // 상세 응답 생성
        return RequestTaskDetailResponse.builder()
            .id(task.getId())
            .title(task.getTitle())
            .imageUrl(task.getImageList().isEmpty() ? null : task.getImageList().get(0))
            .category(task.getCategory())
            .deadline(task.getDeadline())
            .applicants(recruitmentApplicants) // 모집 분야별 지원자 정보
            .build();
    }

}
