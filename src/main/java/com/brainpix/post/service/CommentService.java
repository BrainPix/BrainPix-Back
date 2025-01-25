package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.kafka.service.AlarmEventService;
import com.brainpix.post.converter.CreateCommentDtoConverter;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.CreateCommentDto;
import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.CommentRepository;
import com.brainpix.post.repository.PostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AlarmEventService alarmEventService;

	// 게시글의 댓글 목록 조회
	@Transactional(readOnly = true)
	public GetCommentListDto.Response getCommentList(GetCommentListDto.Parameter parameter) {

		// 게시글 조회
		Post post = postRepository.findById(parameter.getPostId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 게시글에 연관된 모든 댓글을 조회
		Page<Comment> comments = commentRepository.findByParentPostId(post.getId(), parameter.getPageable());

		return GetCommentListDtoConverter.toResponse(comments);
	}

	// 댓글 생성
	@Transactional
	public void createComment(CreateCommentDto.Parameter parameter) {

		// 작성자 조회
		User sender = userRepository.findById(parameter.getSenderId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 게시글 조회
		Post post = postRepository.findById(parameter.getPostId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 댓글 생성
		Comment comment = CreateCommentDtoConverter.toComment(sender, post, parameter.getContent());

		// 댓글 등록
		commentRepository.save(comment);

		// 마지막으로 알람 생성 (게시글 작성자와 댓글 작성자가 다른 경우만 발행)
		if (post.getWriter() != sender) {
			String postType = post instanceof IdeaMarket ? "IdeaMarket" :
				(post instanceof RequestTask ? "RequestTask" : "CollaborationHub");
			alarmEventService.createQnaComment(post.getWriter().getId(), postType, post.getWriter().getName(),
				sender.getName());
		}
	}
}
