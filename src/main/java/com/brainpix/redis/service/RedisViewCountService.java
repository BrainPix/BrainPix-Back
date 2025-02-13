package com.brainpix.redis.service;

import java.time.Duration;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisViewCountService {

	private final RedissonClient redissonClient;
	private final PostRepository postRepository;
	private final String POST_PREFIX = "post:";

	// 조회수 어뷰징 방지를 위한 set 사용
	public void increaseViewCount(Long postId, Long userId) {
		RSet<Long> viewUsers = redissonClient.getSet(POST_PREFIX + postId);
		viewUsers.add(userId);
		viewUsers.expire(Duration.ofDays(1));
	}

	@Scheduled(fixedRate = 60000) // 1분마다 조회수 업데이트
	@Transactional
	public void increaseViewCountToDB() {
		Iterable<String> keys = redissonClient.getKeys().getKeysByPattern(POST_PREFIX + "*");

		for (String key : keys) {
			RSet<Long> viewUsers = redissonClient.getSet(key);

			Long postId = Long.parseLong(key.substring(POST_PREFIX.length()));
			Long viewCount = (long)viewUsers.size();

			// DB에 조회수 반영
			postRepository.updateViewCount(postId, viewCount);

			// 반영 후 삭제
			viewUsers.delete();
		}
	}
}
