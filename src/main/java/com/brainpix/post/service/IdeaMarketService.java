package com.brainpix.post.service;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdeaMarketService {

	private final IdeaMarketRepository ideaMarketRepository;
	private final UserRepository userRepository;

	public Long createIdeaMarket(IdeaMarketCreateDto createDto) {
		User writer = userRepository.findById(1L)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		Price price = Price.builder()
			//.totalQuantity(createDto.getTotalQuantity())
			//.occupiedQuantity(null)
			.price(createDto.getPrice())
			.build();

		IdeaMarket ideaMarket = IdeaMarket.builder()
			.writer(writer)
			.title(createDto.getTitle())
			.content(createDto.getContent())
			.category(createDto.getCategory())
			.specialization(createDto.getSpecialization())
			.ideaMarketType(createDto.getIdeaMarketType())
			.openMyProfile(createDto.getOpenMyProfile())
			.viewCount(0L) // 초기 조회수는 0으로 설정
			.imageList(createDto.getImageList())
			.attachmentFileList(createDto.getAttachmentFileList())
			.postAuth(createDto.getPostAuth())
			.price(price)
			.build();

		IdeaMarket savedIdeaMarket = ideaMarketRepository.save(ideaMarket);
		return savedIdeaMarket.getId();
	}

	public void updateIdeaMarket(Long id, IdeaMarketUpdateDto updateDto) {
		IdeaMarket existingTask = ideaMarketRepository.findById(id)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// CollaborationHub 고유 필드 업데이트
		existingTask.updateIdeaMarketFields(updateDto);

		ideaMarketRepository.save(existingTask);
	}

	public void deleteIdeaMarket(Long id) {
		if (!ideaMarketRepository.existsById(id)) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
		ideaMarketRepository.deleteById(id);
	}
}
