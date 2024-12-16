package com.harshaltaori.blog.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiException extends RuntimeException{

	public ApiException(String message) {
		super(message);
	}
	
}
