package com.brainpix.profile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PortfolioErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.repository.PortfolioRepository;
import com.brainpix.profile.repository.ProfileRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

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
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		Profile profile = profileRepository.findByUserId(user.getId())
			.orElseThrow(() -> new BrainPixException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));

		Portfolio portfolio = request.toEntity(profile);

		portfolioRepository.save(portfolio);

		return portfolio.getId();
	}

	@Transactional
	public void updatePortfolio(long userId, long portfolioId, PortfolioRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new BrainPixException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));

		request.applyTo(portfolio);
	}

	@Transactional
	public void deletePortfolio(long userId, long portfolioId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new BrainPixException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));

		portfolio.validateOwnership(user);

		portfolioRepository.delete(portfolio);
	}

	public Page<PortfolioResponse> findAllMyPortfolios(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		Profile profile = profileRepository.findByUserId(user.getId())
			.orElseThrow(() -> new BrainPixException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));

		Page<Portfolio> portfolio = portfolioRepository.findByProfile(profile, pageable);

		return portfolio.map(PortfolioResponse::from);
	}

	public PortfolioDetailResponse findPortfolioDetail(long userId, long portfolioId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new BrainPixException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));

		Profile profile = profileRepository.findByUserId(user.getId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		portfolio.validateOwnership(user);

		return PortfolioDetailResponse.of(portfolio);
	}
}


