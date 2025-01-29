package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.User;

@Repository
public interface IdeaMarketRepository extends JpaRepository<IdeaMarket, Long>, IdeaMarketCustomRepository {

	// 사용자의 아이디어 등록 횟수 카운트
	Long countByWriterId(Long writerId);

	// 내가 작성한 아이디어마켓 게시글 목록을 writer 기준으로 조회
	Page<IdeaMarket> findByWriter(User writer, Pageable pageable);
}
