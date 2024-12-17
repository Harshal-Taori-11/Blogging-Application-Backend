package com.harshaltaori.blog.configs;

import java.util.List;

public class AppConstants {
	
	public static final String PAGE_NUMBER = "0";
	
	public static final String PAGE_SIZE = "5";
	
	public static final String SORT_BY = "addedOn";
	
	public static final String SORT_DIRECTION = "Ascending";
	
	
	//I am not adding abusive words here we can add them as required
	public static final List<String>OFFENSIVE_WORDS = List.of("idiot","moron","stupid","dumb","hell","bastard","nigga","fuck");
	
	//Jwt constants
	public static final long JWT_VALIDITY = 60*60; // In seconds=1hr
	
	public static final String JWT_SECRET_KEY = "ItsherchoicehahahahaThisIsA256BitSecretKeyForJWTSigning!";
	
	public static final String[] PUBLIC_URLS = {
			"/api/auth/**",
	};

	public static final Integer NORMAL_USER = 501;
	public static final Integer ADMIN_USER = 502;
	
}
