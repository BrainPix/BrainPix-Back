package com.brainpix.post.entity;

import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public abstract class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User writer;

	private String title;
	private String content;
	private Boolean openMyProfile;
	private Long viewCount;

	@Enumerated(EnumType.STRING)
	private PostAuth postAuth;

	@Enumerated(EnumType.STRING)
	private Specialization specialization;

	@BatchSize(size = 10)
	@ElementCollection
	private List<String> imageList;

	@ElementCollection
	private List<String> attachmentFileList;

	public Post(User writer, String title, String content, Boolean openMyProfile, Long viewCount,
		PostAuth postAuth, Specialization specialization, List<String> imageList, List<String> attachmentFileList) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.openMyProfile = openMyProfile;
		this.viewCount = viewCount;
		this.postAuth = postAuth;
		this.specialization = specialization;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}

	public void updateBaseFields(String title, String content, Specialization specialization, Boolean openMyProfile,
		PostAuth postAuth, List<String> imageList, List<String> attachmentFileList) {
		this.title = title;
		this.content = content;
		this.specialization = specialization;
		this.openMyProfile = openMyProfile;
		this.postAuth = postAuth;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}

	// 작성자 검증 메서드
	public void validateWriter(Long userId) {
		if (!this.getWriter().getId().equals(userId)) {
			throw new BrainPixException(PostErrorCode.FORBIDDEN_ACCESS); // 권한 없음 예외
		}
	}

	public String getFirstImage() {
		if (imageList == null || imageList.isEmpty()) {
			return "thumbnail does not exist;";
		}
		return imageList.get(0);
	}
}
