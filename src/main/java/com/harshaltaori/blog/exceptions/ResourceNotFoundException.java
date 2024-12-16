package com.harshaltaori.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	private String fieldName;
	private long fieldValue ;
	
	public ResourceNotFoundException(String fieldName ,long fieldValue) {
		
		super(String.format("%s not found with Id : %s", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String fieldName) {
		
		super(String.format("%s not found ", fieldName));
		this.fieldName = fieldName;
	}
}
