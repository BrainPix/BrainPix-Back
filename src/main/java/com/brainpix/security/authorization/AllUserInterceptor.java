package com.brainpix.security.authorization;

import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.security.authority.BrainpixAuthority;

@Component
public class AllUserInterceptor extends AbstractAuthInterceptor<AllUser, BrainpixAuthority> {

	public AllUserInterceptor() {
		super(AllUser.class, List.of(BrainpixAuthority.INDIVIDUAL, BrainpixAuthority.COMPANY));
	}
}
