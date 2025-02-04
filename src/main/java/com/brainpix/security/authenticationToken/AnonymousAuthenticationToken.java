package com.brainpix.security.authenticationToken;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AnonymousAuthenticationToken extends BrainpixAuthenticationToken {
	public AnonymousAuthenticationToken(Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities, -1L);
	}
}
