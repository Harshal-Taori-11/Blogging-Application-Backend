package com.harshaltaori.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshaltaori.blog.payloads.UserDTO;
import com.harshaltaori.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		
		UserDTO createdUserDTO = this.userService.createUser(userDTO); 
		return new ResponseEntity<UserDTO>(createdUserDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer userId){
		UserDTO userDTO = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDTO);
		
	}
	
//	To be used for deletion below
//	return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), null);
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO , @PathVariable("userId") Integer userId ){
		
		UserDTO updatedUserDTO = this.userService.updateUser(userDTO, userId);
		return ResponseEntity.ok(updatedUserDTO);
	}
}
