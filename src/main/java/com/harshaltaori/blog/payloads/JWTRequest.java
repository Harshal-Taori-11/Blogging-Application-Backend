package com.harshaltaori.blog.payloads;

import lombok.Data;

@Data
public class JWTRequest {
	
	private String username;
	private String password;
	
}
