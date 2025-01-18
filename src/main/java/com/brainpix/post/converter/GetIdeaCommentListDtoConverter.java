package com.brainpix.post.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.brainpix.post.dto.GetIdeaCommentListDto;
import com.brainpix.post.entity.Comment;

public class GetIdeaCommentListDtoConverter {

	public static GetIdeaCommentListDto.Response toResponse(Page<Comment> comments) {

		List<GetIdeaCommentListDto.ResponseData> responseDataList = new ArrayList<>();

		// 댓글의 최대 깊이가 1인 경우만 적용됩니다.
		for(Comment comment : comments) {
			GetIdeaCommentListDto.ResponseData responseData = toResponseData(comment);

			// 자식 댓글인 경우 리스트에서 부모를 꺼내 자식 리스트에 추가
			if(responseData.getParentCommentId() != null && !responseDataList.isEmpty()) {
				GetIdeaCommentListDto.ResponseData parent = responseDataList.get(responseDataList.size() - 1);
				parent.getChildComments().add(responseData);
			} else {
				responseDataList.add(responseData);
			}
		}

		return GetIdeaCommentListDto.Response.builder()
			.responseDataList(responseDataList)
			.totalPages(comments.getTotalPages())
			.totalElements((int)comments.getTotalElements())
			.currentPage(comments.getNumber())
			.currentSize(comments.getNumberOfElements())
			.hasNext(comments.hasNext())
			.build();
	}

	public static GetIdeaCommentListDto.ResponseData toResponseData(Comment comment) {

		return GetIdeaCommentListDto.ResponseData.builder()
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
