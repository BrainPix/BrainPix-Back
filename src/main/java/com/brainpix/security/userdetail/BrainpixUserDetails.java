package com.brainpix.security.userdetail;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.brainpix.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrainpixUserDetails implements UserDetails {

	private final User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(user.getAuthority());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getIdentifier();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	public Long getDatabaseId() {
		return user.getId();
	}
}
