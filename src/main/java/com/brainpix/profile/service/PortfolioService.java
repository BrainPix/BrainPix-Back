package com.brainpix.profile.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.PortfolioErrorCode;
import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.request.SpecializationRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.entity.Specialization;
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
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.USER_NOT_FOUND.getMessage()
			));

		Profile profile = profileRepository.findByUserId(user.getId())
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.RESOURCE_NOT_FOUND.getMessage()
			));

		List<Specialization> specializations = request.specializations().stream()
			.map(SpecializationRequest::toDomain)
			.toList();

		Portfolio portfolio = Portfolio.create(
			profile,
			request.title(),
			specializations,
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
				PortfolioErrorCode.USER_NOT_FOUND.getMessage()
			));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.PORTFOLIO_NOT_FOUND.getMessage()
			));

		List<Specialization> specializations = request.specializations().stream()
			.map(SpecializationRequest::toDomain)
			.toList();

		portfolio.update(
			request.title(),
			specializations,
			request.startDate(),
			request.endDate(),
			request.content()
		);
	}

	@Transactional
	public void deletePortfolio(long userId, long portfolioId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.USER_NOT_FOUND.getMessage()
			));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.PORTFOLIO_NOT_FOUND.getMessage()
			));

		portfolio.validateOwnership(user);

		portfolioRepository.delete(portfolio);
	}

	public Page<PortfolioResponse> findAllMyPortfolios(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.USER_NOT_FOUND.getMessage()
			));

		Profile profile = profileRepository.findByUserId(user.getId())
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.RESOURCE_NOT_FOUND.getMessage()
			));

		Page<Portfolio> portfolio = portfolioRepository.findByProfile(profile, pageable);

		return portfolio.map(PortfolioResponse::from);
	}

	public PortfolioDetailResponse findPortfolioDetail(long userId, long portfolioId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.USER_NOT_FOUND.getMessage()
			));

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.PORTFOLIO_NOT_FOUND.getMessage()
			));

		Profile profile = profileRepository.findByUserId(user.getId())
			.orElseThrow(() -> new IllegalArgumentException(
				PortfolioErrorCode.RESOURCE_NOT_FOUND.getMessage()
			));

		portfolio.validateOwnership(user);

		return PortfolioDetailResponse.of(portfolio);
	}
}


