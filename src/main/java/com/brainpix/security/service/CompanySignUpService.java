package com.brainpix.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.repository.ProfileRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

@Service
public class CompanySignUpService extends SignUpService {

	private final ProfileRepository profileRepository;

	public CompanySignUpService(UserRepository userRepository,
		PasswordEncoder passwordEncoder,
		ProfileRepository profileRepository) {
		super(userRepository, passwordEncoder);
		this.profileRepository = profileRepository;
	}

	@Override
	protected void firstSignupProcess(User user) {
		CompanyProfile profile = CompanyProfile.builder()
			.user(user)
			.specializationList(null)
			.businessInformation(null)
			.openHomepage(true)
			.companyInformations(null)
			.build();

		profileRepository.save(profile);
	}
}
