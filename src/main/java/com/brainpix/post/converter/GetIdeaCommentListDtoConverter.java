package com.brainpix.post.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.dto.GetIdeaCommentListDto;
import com.brainpix.post.entity.Comment;

public class GetIdeaCommentListDtoConverter {

	public static GetIdeaCommentListDto.Parameter toParameter(Long ideaId, Pageable pageable) {
		return GetIdeaCommentListDto.Parameter.builder()
			.ideaId(ideaId)
			.pageable(pageable)
			.build();
	}

	public static GetIdeaCommentListDto.Response toResponse(Page<Comment> comments) {

		List<GetIdeaCommentListDto.Comment> commentList = new ArrayList<>();

		// 댓글의 최대 깊이가 1인 경우만 적용됩니다.
		for(Comment comment : comments) {
			GetIdeaCommentListDto.Comment commentDto = toComment(comment);

			// 자식 댓글인 경우 리스트에서 부모를 꺼내 자식 리스트에 추가
			if(commentDto.getParentCommentId() != null && !commentList.isEmpty()) {
				GetIdeaCommentListDto.Comment parent = commentList.get(commentList.size() - 1);
				parent.getChildComments().add(commentDto);
			} else {
				commentList.add(commentDto);
			}
		}

		return GetIdeaCommentListDto.Response.builder()
			.commentList(commentList)
			.totalPages(comments.getTotalPages())
			.totalElements((int)comments.getTotalElements())
			.currentPage(comments.getNumber())
			.currentSize(comments.getNumberOfElements())
			.hasNext(comments.hasNext())
			.build();
	}

	public static GetIdeaCommentListDto.Comment toComment(Comment comment) {

		return GetIdeaCommentListDto.Comment.builder()
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
