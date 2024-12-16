package com.harshaltaori.blog.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshaltaori.blog.payloads.ApiResponse;

import jakarta.validation.UnexpectedTypeException;

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
	
	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<ApiResponse> unexpectedTypeExceptionHandler(UnexpectedTypeException ex){
		
		String message = ex.getMessage();
		
		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex){
		
		String message;
	    if (ex.getMessage().contains("user_name")) {
	        message = "Username already exists. Please use another username.";
	    } else {
	        message = "A database constraint violation occurred: " + ex.getMessage();
	    }

		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> illegalArguementExceptionHandler(IllegalArgumentException ex){
		
		String message = ex.getMessage();
		
		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> ioExceptionHandler(IOException ex){
		
		String message = ex.getMessage();
		
		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ApiResponse> fileNotFoundExceptionHandler(FileNotFoundException ex){
		
		String message = ex.getMessage();
		
		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<ApiResponse> unsupportedOperationExceptionHandler(UnsupportedOperationException ex){
		
		String message = "Pass some other value for sortBy";
		
		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage() , false),HttpStatus.BAD_REQUEST);
	}
	
	
}

