package com.brainpix.security.authenticationToken;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class BrainpixAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final Long id;

	public BrainpixAuthenticationToken(Object principal, Object credentials, Long id) {
		super(principal, credentials);
		this.id = id;
	}

	public BrainpixAuthenticationToken(Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities, Long id) {
		super(principal, credentials, authorities);
		this.id = id;
	}

	@Override
	public Long getDetails() {
		return id;
	}
}
