package com.brainpix.profile.service;

import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.repository.PortfolioRepository;
import com.brainpix.profile.repository.ProfileRepository;
import com.brainpix.profile.repository.UserRepository;
import com.brainpix.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final ProfileRepository profileRepository;


    @Transactional
    public Long createPortfolio(long userId, PortfolioRequest request){
        User user=userRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException(
                "%d는 존재하지 않는 userId".formatted(userId)));

        Profile profile= profileRepository.findById(user.getProfileId())
            .orElseThrow();
        Portfolio portfolio=request.toEntity(profile);

        portfolioRepository.save(portfolio);

        return portfolio.getId();
    }

    @Transactional
    public void updatePortfolio(long userId,long portfolioId, PortfolioRequest request){

        User user=userRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException(
                "%d는 존재하지 않는 userId".formatted(userId)
            ));

        Portfolio portfolio=portfolioRepository.findById(portfolioId)
            .orElseThrow(()->new IllegalArgumentException(
                "%d는 존재하지 않는 portfolioId".formatted(portfolioId)
            ));

        if (portfolio.getProfile().getId()!=user.getProfileId()){
            throw new IllegalArgumentException("본인 소유의 포트폴리오가 아닙니다");
        }

        request.applyTo(portfolio);
    }

    @Transactional
    public Page<PortfolioResponse> findAllMyPortfolios(long userId, Pageable pageable){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(
                "%d는 존재하지 않는 userId".formatted(userId)));
        Profile profile=profileRepository.findById(user.getProfileId())
            .orElseThrow();

        Page<Portfolio> portfolio = portfolioRepository.findByProfile(profile,pageable);

        return portfolio.map(PortfolioResponse::from);

    }

    @Transactional
    public PortfolioDetailResponse findPortfolioDetail(long userId,long portfolioId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(
                "%d는 존재하지 않는 userId".formatted(userId)));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new IllegalArgumentException(
                "%d는 존재하지 않는 portfolioId".formatted(portfolioId)));

        if(portfolio.getProfile().getId()!=userId){
            throw new IllegalArgumentException("본인 소유의 포트폴리오가 아닙니다.");
        }
        return PortfolioDetailResponse.of(portfolio);
    }

}
