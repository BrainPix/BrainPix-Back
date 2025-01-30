package com.brainpix.profile.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.ProfileErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.PostRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.profile.converter.MyProfileConverter;
import com.brainpix.profile.converter.ProfilePostConverter;
import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.dto.PublicProfileResponseDto;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.repository.CompanyProfileRepository;
import com.brainpix.profile.repository.IndividualProfileRepository;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicProfileService {

	private final UserRepository userRepository;
	private final IndividualProfileRepository individualProfileRepository;
	private final CompanyProfileRepository companyProfileRepository;
	private final MyProfileConverter myProfileConverter;
	private final ProfilePostConverter postConverter;
	private final PostRepository postRepository;
	private final SavedPostRepository savedPostRepository;

	/**
	 * 공개 개인 프로필 조회
	 */
	public IndividualProfileResponseDto getPublicIndividualProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));

		if (!(user instanceof Individual)) {
			throw new BrainPixException(ProfileErrorCode.INVALID_USER_TYPE);
		}

		IndividualProfile profile = individualProfileRepository.findByUser(user)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.PROFILE_NOT_FOUND));

		IndividualProfileResponseDto profileDto = myProfileConverter.toDto(user);

		List<PublicProfileResponseDto.PostPreviewDto> postHistory = postRepository.findByWriter(user).stream()
			.map(post -> {
				if (post instanceof RequestTask) {
					return postConverter.toRequestTaskPreviewDto((RequestTask)post,
						savedPostRepository.countByPostId(post.getId()));
				} else if (post instanceof IdeaMarket) {
					return postConverter.toIdeaMarketPreviewDto((IdeaMarket)post,
						savedPostRepository.countByPostId(post.getId()));
				} else if (post instanceof CollaborationHub) {
					return postConverter.toCollaborationHubPreviewDto((CollaborationHub)post,
						savedPostRepository.countByPostId(post.getId()));
				}
				return null;
			})
			.filter(Objects::nonNull)
			.sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate())) // 최신순 정렬
			.collect(Collectors.toList());

		// 🚨 비공개 항목 처리 (비공개면 빈 값/리스트 반환)
		return IndividualProfileResponseDto.builder()
			.userType(profileDto.getUserType())
			.specializations(profileDto.getSpecializations())
			.name(profileDto.getName())
			.selfIntroduction(profile.getCareerOpen() ? profileDto.getSelfIntroduction() : "")
			.contacts(profile.getContactOpen() ? profileDto.getContacts() : Collections.emptyList())
			.stacks(profile.getStackOpen() ? profileDto.getStacks() : Collections.emptyList())
			.careers(profile.getCareerOpen() ? profileDto.getCareers() : Collections.emptyList())
			.portfolios(profileDto.getPortfolios())
			.build();
	}

	/**
	 * 공개 기업 프로필 조회
	 */
	public CompanyProfileResponseDto getPublicCompanyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));

		if (!(user instanceof Company)) {
			throw new BrainPixException(ProfileErrorCode.INVALID_USER_TYPE);
		}

		CompanyProfile profile = companyProfileRepository.findByUser(user)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.PROFILE_NOT_FOUND));

		CompanyProfileResponseDto profileDto = myProfileConverter.toCompanyDto((Company)user);

		return CompanyProfileResponseDto.builder()
			.userType(profileDto.getUserType())
			.specializations(profileDto.getSpecializations())
			.name(profileDto.getName())
			.selfIntroduction(profile.getOpenInformation() ? profileDto.getSelfIntroduction() : "")
			.businessInformation(profile.getOpenInformation() ? profileDto.getBusinessInformation() : "")
			.companyInformations(profile.getOpenInformation() ? profileDto.getCompanyInformations() :
				Collections.emptyList())
			.portfolios(profileDto.getPortfolios())
			.build();
	}
}