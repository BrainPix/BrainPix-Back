package com.brainpix.profile.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.profile.dto.MyPageResponseDto;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

	private final UserRepository userRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;
	private final RequestTaskPurchasingRepository requestTaskPurchasingRepositoryl;

	public MyPageResponseDto getMyPage(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 분야 (최대 2개)
		List<String> specializations = user.getProfile().getSpecializationList().stream()
			.map(Enum::name)
			.toList();

		// 아이디어 작성 횟수 (아이디어 마켓 게시물 수)
		long ideaCount = ideaMarketRepository.countByWriterId(user.getId());

		// 협업 경험 계산
		long collaborationCount = calculateCollaborationCount(user);

		String selfIntroduction = getSelfIntroduction(user);

		return MyPageResponseDto.builder()
			.name(user.getName())
			.userType(user.getUserType())
			.specializations(specializations)
			.ideaCount(ideaCount)
			.collaborationCount(collaborationCount)
			.selfIntroduction(selfIntroduction)
			.build();
	}

	public Page<String> getMyIdeas(Long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		return ideaMarketRepository.findByWriter(user, pageable)
			.map(IdeaMarket::getTitle);
	}

	private long calculateCollaborationCount(User user) {
		//  초기 멤버 및 승인된 참여자 횟수
		long approvedCollaborations = collectionGatheringRepository.countByJoinerIdAndAccepted(user.getId(), true);
		long initialGatherings = collectionGatheringRepository.countByJoinerIdAndInitialGathering(user.getId(), true);

		// RequestTaskRecruitment: 요청 과제에서 승인된 횟수
		long approvedRequestTasks = requestTaskPurchasingRepositoryl.countByBuyerIdAndAccepted(user.getId(), true);

		return approvedCollaborations + initialGatherings + approvedRequestTasks;
	}

	private String getSelfIntroduction(User user) {
		if (user instanceof Individual) {
			IndividualProfile profile = (IndividualProfile)user.getProfile();
			return profile.getSelfIntroduction();
		} else if (user instanceof Company) {
			CompanyProfile profile = (CompanyProfile)user.getProfile();
			return profile.getBusinessInformation();
		}
		return ""; // 기본값으로 빈 문자열 반환
	}
}
