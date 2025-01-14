package com.brainpix.profile.service.portfolio;

import com.brainpix.profile.dto.portfoliodto.PortfolioCreateRequest;
import com.brainpix.profile.dto.portfoliodto.PortfolioDetailResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioListResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioUpdateRequest;
import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.repository.PortfolioRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    /**
     * 포트폴리오 리스트 조회
     * @param userId 사용자 ID
     * @return 포트폴리오 리스트
     */
    @Transactional(readOnly = true)
    public List<PortfolioListResponse> getPortfolioList(Long userId) {
        // User 엔티티에서 Profile 가져오기
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Profile profile = user.getProfile();

        // 포트폴리오 데이터 조회 및 미리보기 변환
        return portfolioRepository.findByProfileId(profile.getId()).stream()
            .map(portfolio -> PortfolioListResponse.builder()
                .id(portfolio.getId())
                .imageUrl(portfolio.getImageUrl()) // 대표 이미지 URL 설정
                .build())
            .collect(Collectors.toList());

    }

    /**
     * 포트폴리오 상세 조회
     * @param portfolioId 포트폴리오 ID
     * @return 포트폴리오 상세 정보
     */
    @Transactional(readOnly = true)
    public PortfolioDetailResponse getPortfolioDetail(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        return PortfolioDetailResponse.builder()
            .id(portfolio.getId())
            .title(portfolio.getTitle())
            .specializations(portfolio.getSpecializationList())
            .startDate(portfolio.getStartDate())
            .endDate(portfolio.getEndDate())
            .content(portfolio.getContent())
            .imageUrl(portfolio.getImageUrl())
            .build();
    }


    /**
     * 포트폴리오 수정
     * @param portfolioId 포트폴리오 ID
     * @param request 포트폴리오 수정 요청 데이터
     */
    @Transactional
    public void updatePortfolio(Long portfolioId, PortfolioUpdateRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        // 엔티티에 업데이트 메서드 호출
        portfolio.updatePortfolio(
            request.getTitle(),
            request.getSpecializations(),
            request.getStartDate(),
            request.getEndDate(),
            request.getContent(),
            request.getImageUrl()
        );
    }

    /**
     * 포트폴리오 추가
     * @param userId 사용자 ID
     * @param request 포트폴리오 추가 요청 데이터
     */
    @Transactional
    public void createPortfolio(Long userId, PortfolioCreateRequest request) {
        // User 엔티티에서 Profile 가져오기
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Profile profile = user.getProfile();

        // 새로운 포트폴리오 생성 및 저장
        Portfolio portfolio = Portfolio.builder()
            .title(request.getTitle())
            .specializationList(request.getSpecializations())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .content(request.getContent())
            .profile(profile)
            .imageUrl(request.getImageUrl())
            .build();

        portfolioRepository.save(portfolio);
    }

    /**
     * 포트폴리오 삭제
     * @param portfolioId 포트폴리오 ID
     */
    @Transactional
    public void deletePortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        // 삭제
        portfolioRepository.delete(portfolio);
    }
}