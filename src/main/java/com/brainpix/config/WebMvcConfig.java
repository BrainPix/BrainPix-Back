package com.brainpix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.brainpix.security.authorization.AllUserInterceptor;
import com.brainpix.security.authorization.CompanyInterceptor;
import com.brainpix.security.authorization.IndividualInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	private final AllUserInterceptor allUserInterceptor;
	private final CompanyInterceptor companyInterceptor;
	private final IndividualInterceptor individualInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(allUserInterceptor);
		registry.addInterceptor(companyInterceptor);
		registry.addInterceptor(individualInterceptor);
	}
}
