package com.brainpix.post.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.post.entity.request_task.RequestTaskType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskUpdateDto extends PostDto {

	@Future(message = "마감일은 현재 날짜보다 미래여야 합니다.")
	@NotNull(message = "마감일은 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;

	@NotNull(message = "요청 과제 유형은 필수입니다.")
	private RequestTaskType requestTaskType;
}
