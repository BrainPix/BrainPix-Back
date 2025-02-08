package com.brainpix.post.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.entity.Comment;

public class GetCommentListDtoConverter {

	public static GetCommentListDto.Parameter toParameter(Long userId, Long postId, Pageable pageable) {
		return GetCommentListDto.Parameter.builder()
			.userId(userId)
			.postId(postId)
			.pageable(pageable)
			.build();
	}

	public static CommonPageResponse<GetCommentListDto.Comment> toResponse(Page<Comment> comments, Pageable pageable,
		List<Boolean> isMyComments) {

		List<GetCommentListDto.Comment> commentDtoList = new ArrayList<>();

		List<Comment> commentList = comments.getContent();

		for (int i = 0; i < commentList.size(); i++) {
			GetCommentListDto.Comment commentDto = GetCommentListDtoConverter.toComment(commentList.get(i),
				isMyComments.get(i));
			// 자식 댓글인 경우 리스트에서 부모를 꺼내 자식 리스트에 추가
			if (commentDto.getParentCommentId() != null && !commentDtoList.isEmpty()) {
				GetCommentListDto.Comment parent = commentDtoList.get(commentDtoList.size() - 1);
				parent.getChildComments().add(commentDto);
			} else {
				commentDtoList.add(commentDto);
			}
		}

		// 공통 페이징 응답에 담길 결과
		PageImpl<GetCommentListDto.Comment> response = new PageImpl<>(commentDtoList, pageable,
			comments.getTotalElements());

		return CommonPageResponse.of(response);
	}

	public static GetCommentListDto.Comment toComment(Comment comment, Boolean isMyComment) {

		return GetCommentListDto.Comment.builder()
			.commentId(comment.getId())
			.writerId(comment.getWriter().getId())
			.content(comment.getContent())
			.writerName(comment.getWriter().getName())
			.parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
			.childComments(new ArrayList<>())
			.createdDate(comment.getCreatedAt().toLocalDate())
			.isMyComment(isMyComment)
			.build();
	}
}
