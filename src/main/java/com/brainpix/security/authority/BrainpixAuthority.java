package com.brainpix.security.authority;

import org.springframework.security.core.GrantedAuthority;

public enum BrainpixAuthority implements GrantedAuthority {
	INDIVIDUAL, COMPANY, ANONYMOUS;

	@Override
	public String getAuthority() {
		return name();
	}
}
