package com.brainpix.security.tokenManger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.brainpix.security.authenticationToken.BrainpixAuthenticationToken;
import com.brainpix.security.authenticationToken.CompanyAuthenticationToken;
import com.brainpix.security.authenticationToken.IndividualAuthenticationToken;
import com.brainpix.security.authority.BrainpixAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenManager implements TokenManager {

	private final SecretKey secretKey;

	public JwtTokenManager(@Value("${jwt.secret}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public BrainpixAuthenticationToken readToken(String token) throws
		SignatureException, UnsupportedJwtException, MalformedJwtException, ExpiredJwtException {

		JwtParser jwtParser = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build();
		Claims claims = jwtParser.parseClaimsJws(token)
			.getBody();

		String identifier = claims.get("identifier", String.class); // 로그인 시 사용하는 id값
		Long id = claims.get("id", Long.class); // 데이터베이스에서 사용되는 유저의 id값
		List<String> authorities = claims.get("authorities", List.class);

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		BrainpixAuthenticationToken authenticationToken = null;

		if (authorities.get(0).equals(BrainpixAuthority.INDIVIDUAL.name())) {
			grantedAuthorities.add(BrainpixAuthority.INDIVIDUAL);
			authenticationToken = new IndividualAuthenticationToken(identifier, null,
				grantedAuthorities,
				id);
		} else if (authorities.get(0).equals(BrainpixAuthority.COMPANY.name())) {
			grantedAuthorities.add(BrainpixAuthority.COMPANY);
			authenticationToken = new CompanyAuthenticationToken(identifier, null,
				grantedAuthorities,
				id);
		} else {
			throw new MalformedJwtException("Brainpix에 등록되지 않은 사용자입니다.");
		}
		return authenticationToken;
	}

	@Override
	public String writeToken(Authentication authentication) {
		return Jwts.builder()
			.setHeader(Map.of(
				"provider", "brainpix",
				"type", "accessToken"
			))
			.setClaims(Map.of(
				"identifier", authentication.getPrincipal(),
				"authorities", authentication.getAuthorities(),
				"id", authentication.getDetails()
			))
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // ms 단위, 24시간
			.signWith(secretKey)
			.compact();
	}
}
