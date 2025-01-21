package com.brainpix.kafka.service;

import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.brainpix.alarm.dto.AlarmEventDto;
import com.brainpix.alarm.template.AlarmType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmEventService {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void createQnaComment(Long receiverId, String postType, String postWriter, String commentWriter) {
		Map<String, String> parameters = Map.of("postType", postType, "postWriter", postWriter, "commentWriter",
			commentWriter);
		publishAlarmEvent(receiverId, AlarmType.QNA_COMMENT, parameters);
	}

	public void createQnaCommentReply(Long receiverId, String postType, String postWriter, String commentWriter) {
		Map<String, String> parameters = Map.of("postType", postType, "postWriter", postWriter, "commentWriter",
			commentWriter);
		publishAlarmEvent(receiverId, AlarmType.QNA_COMMENT_REPLY, parameters);
	}

	public void publishIdeaSold(Long receiverId, String postWriter) {
		Map<String, String> parameters = Map.of("postWriter", postWriter);
		publishAlarmEvent(receiverId, AlarmType.IDEA_SOLD, parameters);
	}

	public void publishRequestTaskApply(Long receiverId, String postWriter, String applicant) {
		Map<String, String> parameters = Map.of("postWriter", postWriter, "applicant", applicant);
		publishAlarmEvent(receiverId, AlarmType.REQUEST_TASK_APPLY, parameters);
	}

	public void publishRequestTaskApprove(Long receiverId, String applicant) {
		Map<String, String> parameters = Map.of("applicant", applicant);
		publishAlarmEvent(receiverId, AlarmType.REQUEST_TASK_APPROVE, parameters);
	}

	public void publishCollaborationTaskApply(Long receiverId, String applicant, String postWriter) {
		Map<String, String> parameters = Map.of("applicant", applicant, "postWriter", postWriter);
		publishAlarmEvent(receiverId, AlarmType.COLLABORATION_TASK_APPLY, parameters);
	}

	public void publishCollaborationTaskApprove(Long receiverId, String applicant, String postWriter) {
		Map<String, String> parameters = Map.of("applicant", applicant, "postWriter", postWriter);
		publishAlarmEvent(receiverId, AlarmType.COLLABORATION_TASK_APPROVE, parameters);
	}

	public void publishCollaborationTaskReject(Long receiverId, String applicant, String postWriter) {
		Map<String, String> parameters = Map.of("applicant", applicant, "postWriter", postWriter);
		publishAlarmEvent(receiverId, AlarmType.COLLABORATION_TASK_REJECT, parameters);
	}

	private void publishAlarmEvent(Long receiverId, AlarmType alarmType, Map<String, String> parameters) {
		AlarmEventDto alarmEventDto = AlarmEventDto.builder()
			.receiverId(receiverId)
			.alarmType(alarmType)
			.parameters(parameters)
			.build();

		kafkaTemplate.send("alarm-create", alarmEventDto);

		log.info("AlarmEvent published: {}", alarmEventDto);
	}
}
