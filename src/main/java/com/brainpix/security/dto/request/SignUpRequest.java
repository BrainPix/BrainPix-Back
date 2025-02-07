package com.brainpix.security.dto.request;

import java.time.LocalDate;

import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignUpRequest {

	@Getter
	@NoArgsConstructor
	public abstract static class CommonSignUpRequest {
		protected String id;
		protected String password;
		protected String name;
		protected LocalDate birthday;
		protected String email;
		protected String emailToken;

		public abstract User toEntity(String encodedPassword);

		public abstract String myNickname();
	}

	@Getter
	@NoArgsConstructor
	public static class PersonalSignUpRequest extends CommonSignUpRequest {
		private String nickName;

		@Override
		public User toEntity(String encodedPassword) {
			return Individual.builder()
				.identifier(id)
				.password(encodedPassword)
				.name(name)
				.nickName(nickName)
				.birthday(birthday)
				.email(email)
				.profileImage("https://placehold.co/400x400")
				.build();
		}

		@Override
		public String myNickname() {
			return nickName;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class CompanySignUpRequest extends CommonSignUpRequest {
		private String companyName;
		private String position;

		@Override
		public User toEntity(String encodedPassword) {
			return Company.builder()
				.identifier(id)
				.password(encodedPassword)
				.name(name)
				.nickName(companyName)
				.birthday(birthday)
				.email(email)
				.position(position)
				.profileImage("https://placehold.co/400x400")
				.build();
		}

		@Override
		public String myNickname() {
			return companyName;
		}
	}
}
