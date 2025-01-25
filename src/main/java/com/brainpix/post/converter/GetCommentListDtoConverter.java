package com.brainpix.post.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.entity.Comment;

public class GetCommentListDtoConverter {

	public static GetCommentListDto.Parameter toParameter(Long postId, Pageable pageable) {
		return GetCommentListDto.Parameter.builder()
			.postId(postId)
			.pageable(pageable)
			.build();
	}

	public static GetCommentListDto.Response toResponse(Page<Comment> comments) {

		List<GetCommentListDto.Comment> commentList = new ArrayList<>();

		// 댓글의 최대 깊이가 1인 경우만 적용됩니다.
		for (Comment comment : comments) {
			GetCommentListDto.Comment commentDto = toComment(comment);

			// 자식 댓글인 경우 리스트에서 부모를 꺼내 자식 리스트에 추가
			if (commentDto.getParentCommentId() != null && !commentList.isEmpty()) {
				GetCommentListDto.Comment parent = commentList.get(commentList.size() - 1);
				parent.getChildComments().add(commentDto);
			} else {
				commentList.add(commentDto);
			}
		}

		return GetCommentListDto.Response.builder()
			.commentList(commentList)
			.totalPages(comments.getTotalPages())
			.totalElements((int)comments.getTotalElements())
			.currentPage(comments.getNumber())
			.currentSize(comments.getNumberOfElements())
			.hasNext(comments.hasNext())
			.build();
	}

	public static GetCommentListDto.Comment toComment(Comment comment) {

		return GetCommentListDto.Comment.builder()
			.commentId(comment.getId())
			.writerId(comment.getWriter().getId())
			.content(comment.getContent())
			.writerName(comment.getWriter().getName())
			.parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
			.childComments(new ArrayList<>())
			.createdDate(comment.getCreatedAt().toLocalDate())
			.build();
	}
}
