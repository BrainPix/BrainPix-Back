package com.brainpix.security.contextholder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.brainpix.api.code.error.AuthorityErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.security.authority.BrainpixAuthority;

public class GetAuthenticationInfo {

	public static Long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Long)authentication.getDetails();
	}

	public static BrainpixAuthority getAuthority() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().stream()
			.map(BrainpixAuthority.class::cast)
			.findFirst()
			.orElseThrow(() -> new BrainPixException(AuthorityErrorCode.AUTHORITY_ERROR_CODE));
	}
}
