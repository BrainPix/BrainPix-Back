package com.brainpix.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import com.brainpix.security.provider.BrainpixUserAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

	private final BrainpixUserAuthenticationProvider brainpixUserAuthenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		List<AuthenticationProvider> providerList = new ArrayList<>();
		providerList.add(brainpixUserAuthenticationProvider);
		return new ProviderManager(providerList);
	}
}
