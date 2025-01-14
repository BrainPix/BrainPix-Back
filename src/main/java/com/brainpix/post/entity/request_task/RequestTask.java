package com.brainpix.post.entity.request_task;

import java.util.List;

import com.brainpix.post.entity.BasePost;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RequestTask extends BasePost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder
	public RequestTask(User writer, String title, String contest, String category, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList) {
		super(writer, title, contest, category, openMyProfile, viewCount, imageList, attachmentFileList);
	}
}
