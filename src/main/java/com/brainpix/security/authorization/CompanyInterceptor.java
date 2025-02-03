package com.brainpix.security.authorization;

import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.security.authority.BrainpixAuthority;

@Component
public class CompanyInterceptor extends AbstractAuthInterceptor<Company, BrainpixAuthority> {

	public CompanyInterceptor() {
		super(Company.class, List.of(BrainpixAuthority.COMPANY));
	}
}