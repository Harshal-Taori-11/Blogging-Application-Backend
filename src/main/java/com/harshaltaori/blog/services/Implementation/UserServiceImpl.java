package com.harshaltaori.blog.services.Implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.payloads.UserDTO;
import com.harshaltaori.blog.repositories.UserRepository;
import com.harshaltaori.blog.services.UserService;
import com.harshaltaori.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private User userDTOToUser(UserDTO userDTO) {
		return modelMapper.map(userDTO,User.class);
	}
	
	private UserDTO userToUserDTO(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		User user = this.userDTOToUser(userDTO);
		User savedUser = this.userRepository.save(user);
		
		return userToUserDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , userId));
		
		user.setUserName(userDTO.getUserName());
		user.setPassword(userDTO.getPassword());
		user.setEmailId(userDTO.getEmailId());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser = this.userRepository.save(user);
		
		return userToUserDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , userId));
	
		return userToUserDTO(user);
	}

}
