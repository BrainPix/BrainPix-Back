package com.brainpix.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.brainpix.security.filter.JwtAuthenticationFilter;
import com.brainpix.security.tokenManger.TokenManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final TokenManager tokenManager;

	@Bean
	public SecurityFilterChain publicResourceConfig(HttpSecurity http) throws Exception {
		http.formLogin(FormLoginConfigurer::disable);
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(
			cors -> cors.configurationSource(corsConfigurationSource())
		);
		http.addFilterAt(new JwtAuthenticationFilter(tokenManager), BasicAuthenticationFilter.class);
		http.authorizeHttpRequests(
			(authorizeRequests)
				-> authorizeRequests.anyRequest().permitAll()
		);
		return http.build();
	}

	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");

		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
