package com.harshaltaori.blog.services;

import com.harshaltaori.blog.payloads.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(UserDTO userDTO, Integer userId);
	
	UserDTO getUserById(Integer userId);
}
