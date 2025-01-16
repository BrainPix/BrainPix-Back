package com.brainpix.alarm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.brainpix.alarm.model.Alarm;

public interface AlarmRepository extends MongoRepository<Alarm, String> {
	Page<Alarm> findByReceiverIdAndTrashed(Long receiverId, boolean trashed, Pageable pageable);

	List<Alarm> findAllByReceiverIdAndTrashed(Long receiverId, boolean trashed);

	Long countAlarmByReceiverIdAndIsRead(Long receiverId, Boolean isRead);
}
