package com.brainpix.profile.service;

import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
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

		// 비공개 항목 처리 (비공개면 빈 값/리스트 반환)
		return IndividualProfileResponseDto.builder()
			.userId(profileDto.getUserId())
			.profileImage(profileDto.getProfileImage())
			.userType(profileDto.getUserType())
			.specializations(profileDto.getSpecializations())
			.name(profileDto.getName())
			.selfIntroduction(profileDto.getSelfIntroduction())
			.contacts(profileDto.getContacts().stream()
				.filter(IndividualProfileResponseDto.ContactDto::getIsPublic)
				.toList())
			.stacks(profile.getStackOpen() ? profileDto.getStacks() : Collections.emptyList())
			.careers(profile.getCareerOpen() ? profileDto.getCareers() : Collections.emptyList())
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
			.userId(profileDto.getUserId())
			.imageUrl(profileDto.getImageUrl())
			.userType(profileDto.getUserType())
			.specializations(profileDto.getSpecializations())
			.name(profileDto.getName())
			.selfIntroduction(profileDto.getSelfIntroduction())
			.businessInformation(profileDto.getBusinessInformation())
			.companyInformations(profile.getOpenInformation() ? profileDto.getCompanyInformations() :
				Collections.emptyList())
			.build();
	}

	public Page<PublicProfileResponseDto.PostPreviewDto> getPostsByUser(Long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(
				com.brainpix.api.code.error.CommonErrorCode.RESOURCE_NOT_FOUND));

		return postRepository.findByWriter(user, pageable)
			.map(post -> {
				long savedCount = savedPostRepository.countByPostId(post.getId());
				if (post instanceof RequestTask) {
					return postConverter.toRequestTaskPreviewDto((RequestTask)post, savedCount);
				} else if (post instanceof IdeaMarket) {
					return postConverter.toIdeaMarketPreviewDto((IdeaMarket)post, savedCount);
				} else if (post instanceof CollaborationHub) {
					return postConverter.toCollaborationHubPreviewDto((CollaborationHub)post, savedCount);
				}
				throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
			});
	}

}