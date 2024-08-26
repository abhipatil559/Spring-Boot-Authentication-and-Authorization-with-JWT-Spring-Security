package com.spring3.Solution.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spring3.Solution.Service.LoginService;
import com.spring3.Solution.Service.UserService;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil implements Serializable {
	private static final long serialVersionUID = 654352132132L;

	public static final long JWT_TOKEN_VALIDITY = 500 * 60 * 60;

//	private final String secretKey = "randomkey123asdfghjkl12345678910";
	public static final byte[] secureKeyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
	@Autowired
	LoginService loginService;
	/*
	 * Gets the Username(email) of the user from token
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/*
	 * Retrieves the expiry of the token
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = this.getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
		// return null;
	}

	/*
	 * Secret key will be required for retrieving data from token
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secureKeyBytes).parseClaimsJws(token).getBody();
	}

	/*
	 * Check if the token has expired
	 */
	private Boolean isTokenExpired(String token) {
		return this.getExpirationDateFromToken(token).before(new Date());
	}

	@Autowired
	UserService userService;

	// generate token for user
	public String generateToken(UserDetails u) {
		
		
		
		return Jwts.builder().setSubject(u.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, this.secureKeyBytes).compact();
	}

	/*
	 * Generate the token from the claims and required details
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setSubject(subject).setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, this.secureKeyBytes).compact();
	}

	/*
	 * Check if the provided JWT token is valid or not
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		String us = this.getUsernameFromToken(token);

		return (userDetails.getUsername().equals(us) && !this.isTokenExpired(token));
	}
}
