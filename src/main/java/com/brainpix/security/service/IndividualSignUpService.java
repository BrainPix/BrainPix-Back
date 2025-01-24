package com.brainpix.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.repository.ProfileRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

@Service
public class IndividualSignUpService extends SignUpService {

	public final ProfileRepository profileRepository;

	public IndividualSignUpService(UserRepository userRepository,
		PasswordEncoder passwordEncoder,
		ProfileRepository profileRepository) {
		super(userRepository, passwordEncoder);
		this.profileRepository = profileRepository;
	}

	@Override
	protected void firstSignupProcess(User user) {
		IndividualProfile profile = IndividualProfile.builder()
			.user(user)
			.specializationList(null)
			.contactOpen(true)
			.careerOpen(true)
			.stackOpen(true)
			.build();

		profileRepository.save(profile);
	}
}
