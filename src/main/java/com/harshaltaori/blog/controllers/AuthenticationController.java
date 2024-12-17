package com.harshaltaori.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshaltaori.blog.payloads.JWTRequest;
import com.harshaltaori.blog.payloads.JWTResponse;
import com.harshaltaori.blog.payloads.UserInputDto;
import com.harshaltaori.blog.payloads.UserOutputDto;
import com.harshaltaori.blog.secuirty.JwtTokenHelper;
import com.harshaltaori.blog.services.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserOutputDto> registerUser(@RequestBody UserInputDto userInputDto){
		
		UserOutputDto createdDto = this.userService.createUser(userInputDto);
		return new ResponseEntity<UserOutputDto>(createdDto,HttpStatus.CREATED);
	}
	
	private void authenticate(String userName, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
		
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			throw new InvalidDataAccessApiUsageException("Invalid Username or Password!!");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<JWTResponse> jwtCreation(@RequestBody JWTRequest jwtRequest){
		
		this.authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
		
		UserDetails details = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(details);
		
		JWTResponse jwtToken = new JWTResponse();
		jwtToken.setToken(token);
		
		return ResponseEntity.ok(jwtToken);
		
	}
	
	
}
