package com.brainpix.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.brainpix.security.authorization.AllUserInterceptor;
import com.brainpix.security.authorization.CompanyInterceptor;
import com.brainpix.security.authorization.IndividualInterceptor;
import com.brainpix.security.authorization.UserIdArgumentResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	private final AllUserInterceptor allUserInterceptor;
	private final CompanyInterceptor companyInterceptor;
	private final IndividualInterceptor individualInterceptor;
	private final UserIdArgumentResolver userIdArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userIdArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(allUserInterceptor);
		registry.addInterceptor(companyInterceptor);
		registry.addInterceptor(individualInterceptor);
	}
}
