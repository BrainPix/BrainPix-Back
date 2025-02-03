package com.brainpix.security.authenticationToken;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class IndividualAuthenticationToken extends BrainpixAuthenticationToken {

	public IndividualAuthenticationToken(Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities, Long memberId) {
		super(principal, credentials, authorities, memberId);
	}
}
