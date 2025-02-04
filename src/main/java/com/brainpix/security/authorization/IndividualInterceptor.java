package com.brainpix.security.authorization;

import java.util.List;

import org.springframework.stereotype.Component;

import com.brainpix.security.authority.BrainpixAuthority;

@Component
public class IndividualInterceptor extends AbstractAuthInterceptor<Individual, BrainpixAuthority> {

	public IndividualInterceptor() {
		super(Individual.class, List.of(BrainpixAuthority.INDIVIDUAL));
	}
}
