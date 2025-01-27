package com.brainpix.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.kafka.service.AlarmEventService;
import com.brainpix.post.converter.CreateCommentDtoConverter;
import com.brainpix.post.converter.CreateReplyDtoConverter;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.CreateCommentDto;
import com.brainpix.post.dto.CreateReplyDto;
import com.brainpix.post.dto.DeleteCommentDto;
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

		// 마지막으로 알람 생성 (수신자와 송신자가 다른 경우만 발행)
		if (post.getWriter() != sender) {
			String postType = post instanceof IdeaMarket ? "IdeaMarket" :
				(post instanceof RequestTask ? "RequestTask" : "CollaborationHub");
			alarmEventService.createQnaComment(post.getWriter().getId(), postType, post.getWriter().getName(),
				sender.getName());
		}
	}

	// 대댓글 생성
	@Transactional
	public void createReply(CreateReplyDto.Parameter parameter) {

		// 작성자 조회
		User sender = userRepository.findById(parameter.getSenderId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 게시글 조회
		Post post = postRepository.findById(parameter.getPostId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 부모 댓글 조회
		Comment parentComment = commentRepository.findById(parameter.getCommentId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 대댓글 생성
		Comment reply = CreateReplyDtoConverter.toComment(sender, post, parentComment, parameter.getContent());

		// 대댓글 등록
		commentRepository.save(reply);

		// 마지막으로 알람 생성 (수신자와 송신자가 다른 경우만 발행)
		if (parentComment.getWriter() != sender) {
			String postType = post instanceof IdeaMarket ? "IdeaMarket" :
				(post instanceof RequestTask ? "RequestTask" : "CollaborationHub");
			alarmEventService.createQnaCommentReply(parentComment.getWriter().getId(), postType,
				parentComment.getWriter().getName(), sender.getName());
		}
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(DeleteCommentDto.Parameter parameter) {

		// 작성자 조회
		User writer = userRepository.findById(parameter.getWriterId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 댓글 조회
		Comment comment = commentRepository.findById(parameter.getCommentId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 댓글 작성자인지 검증
		if (comment.getWriter() != writer) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		// 자식 댓글 삭제
		List<Comment> childComments = commentRepository.findAllByParentCommentId(comment.getId());
		childComments
			.forEach(commentRepository::delete);

		// 댓글
		commentRepository.delete(comment);
	}
}
