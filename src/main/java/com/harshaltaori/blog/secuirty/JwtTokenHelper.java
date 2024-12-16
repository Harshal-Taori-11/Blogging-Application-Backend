package com.harshaltaori.blog.secuirty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.harshaltaori.blog.configs.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {
	
	private SecretKey generateKey() {
		byte[] decode = Decoders.BASE64.decode(AppConstants.JWT_SECRET_KEY);
		
		return Keys.hmacShaKeyFor(decode);
	}
	
	

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		return doGenerateToken(claims , userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String,Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + AppConstants.JWT_VALIDITY * 1000))
				.signWith(generateKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(generateKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUserNameFromToken(String token) {
		return extractClaims(token, Claims:: getSubject );
	}
	
	public boolean isTokenExpired(String token) {
		final Date expiration = extractClaims(token, Claims:: getExpiration);
		
		return expiration.before(new Date());
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
