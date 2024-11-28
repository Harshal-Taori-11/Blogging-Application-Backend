package com.harshaltaori.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshaltaori.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler( ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message , false);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArguementNotValidExceptionHandler(MethodArgumentNotValidException ex){
		
		Map<String, String> exMap = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fielName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			exMap.put(fielName, message);
		});
		
		return new ResponseEntity<>(exMap,HttpStatus.BAD_REQUEST);
	}
}

