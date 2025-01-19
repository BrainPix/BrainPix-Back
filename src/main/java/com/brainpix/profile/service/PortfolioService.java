package com.brainpix.profile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.request.SpecializationRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.repository.PortfolioRepository;
import com.brainpix.profile.repository.ProfileRepository;
import com.brainpix.profile.repository.UserRepository;
import com.brainpix.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

	private final UserRepository userRepository;
	private final PortfolioRepository portfolioRepository;
	private final ProfileRepository profileRepository;

	@Transactional
	public Long createPortfolio(long userId, PortfolioRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				"%d는 존재하지 않는 userId".formatted(userId)
			));

		Profile profile = profileRepository.findById(user.getProfileId())
			.orElseThrow();

		Portfolio portfolio = Portfolio.create(
			profile,
			request.title(),
			request.specializations().stream()
				.map(SpecializationRequest::toDomain)
				.toList(),
			request.startDate(),
			request.endDate(),
			request.content()
		);

		portfolioRepository.save(portfolio);

		return portfolio.getId();
	}

	@Transactional
	public void updatePortfolio(long userId, long portfolioId, PortfolioRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				"%d는 존재하지 않는 userId".formatted(userId)
			));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new IllegalArgumentException(
				"%d는 존재하지 않는 portfolioId".formatted(portfolioId)
			));

		if (!portfolio.isOwnedBy(user)) { // 엔티티 메서드 호출
			throw new IllegalArgumentException("본인 소유의 포트폴리오가 아닙니다");
		}
		portfolio.update(
			request.title(),
			request.specializations().stream()
				.map(SpecializationRequest::toDomain)
				.toList(),
			request.startDate(),
			request.endDate(),
			request.content()
		);
	}

	public Page<PortfolioResponse> findAllMyPortfolios(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				"%d는 존재하지 않는 userId".formatted(userId)));
		Profile profile = profileRepository.findById(user.getProfileId())
			.orElseThrow();

		Page<Portfolio> portfolio = portfolioRepository.findByProfile(profile, pageable);

		return portfolio.map(PortfolioResponse::from);

	}

	public PortfolioDetailResponse findPortfolioDetail(long userId, long portfolioId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				"%d는 존재하지 않는 userId".formatted(userId)));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new IllegalArgumentException(
				"%d는 존재하지 않는 portfolioId".formatted(portfolioId)));

		if (!portfolio.isOwnedBy(user)) { // 엔티티 메서드 호출
			throw new IllegalArgumentException("본인 소유의 포트폴리오가 아닙니다");
		}
		return PortfolioDetailResponse.of(portfolio);
	}

}
