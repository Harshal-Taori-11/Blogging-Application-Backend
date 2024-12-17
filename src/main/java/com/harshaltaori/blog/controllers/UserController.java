package com.harshaltaori.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshaltaori.blog.payloads.UserInputDto;
import com.harshaltaori.blog.payloads.UserOutputDto;
import com.harshaltaori.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
//	@PostMapping("/")
//	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
//		
//		UserDto createdUserDto = this.userService.createUser(userDto); 
//		return new ResponseEntity<UserDto>(createdUserDto,HttpStatus.CREATED);
//	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserOutputDto> getUser(@PathVariable("userId") Integer userId){
		UserOutputDto userDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
		
	}
	
	@PutMapping("/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<UserOutputDto> updateUser(@Valid @RequestBody UserInputDto userInputDto , @PathVariable("userId") Integer userId ){
		
		UserOutputDto updatedUserDto = this.userService.updateUser(userInputDto, userId);
		return ResponseEntity.ok(updatedUserDto);
	}
}
