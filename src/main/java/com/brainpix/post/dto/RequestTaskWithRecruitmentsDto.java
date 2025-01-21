package com.brainpix.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskWithRecruitmentsDto { //과제 글, 모집 한 번에 받을 때 DTO 분리해서 받기
	private RequestTaskCreateDto requestTaskCreateDto;
	private List<RequestTaskRecruitmentDto> recruitments;
}
