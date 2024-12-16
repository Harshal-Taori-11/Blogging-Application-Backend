package com.harshaltaori.blog.services.Implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.models.Roles;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.payloads.UserDto;
import com.harshaltaori.blog.repositories.RoleRepository;
import com.harshaltaori.blog.repositories.UserRepository;
import com.harshaltaori.blog.services.UserService;
import com.harshaltaori.blog.configs.AppConstants;
import com.harshaltaori.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	private User userDtoToUser(UserDto userDto) {
		return modelMapper.map(userDto,User.class);
	}
	
	private UserDto userToUserDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}
	

	@Override
	public UserDto createUser(UserDto userDto) {	
		
		User user = this.userDtoToUser(userDto);
		
		if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("Username already exists!");
        }
		
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		
		Roles role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User savedUser = this.userRepository.save(user);
		
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		if (userRepository.existsByName(userDto.getName())) {
            throw new IllegalArgumentException("Username already exists!");
        }
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , userId));
		
		user.setName(userDto.getName());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setEmailId(userDto.getEmailId());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepository.save(user);
		
		return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , userId));
	
		return this.userToUserDto(user);
	}

}
