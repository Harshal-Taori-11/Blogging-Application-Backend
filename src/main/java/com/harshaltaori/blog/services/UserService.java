package com.harshaltaori.blog.services;

import com.harshaltaori.blog.payloads.UserInputDto;
import com.harshaltaori.blog.payloads.UserOutputDto;

public interface UserService {
	
	UserOutputDto createUser(UserInputDto userDto);
	
	UserOutputDto
	updateUser(UserInputDto userDto, Integer userId);
	
	UserOutputDto getUserById(Integer userId);
}
