package com.harshaltaori.blog.services.Implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.models.Roles;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.payloads.UserInputDto;
import com.harshaltaori.blog.payloads.UserOutputDto;
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
	
	
	private User userInputDtoToUser(UserInputDto userInputDto) {
		return modelMapper.map(userInputDto,User.class);
	}
	
	private UserOutputDto userToUserOutputDto(User user) {
		return modelMapper.map(user, UserOutputDto.class);
	}
	

	@Override
	public UserOutputDto createUser(UserInputDto userInputDto) {	
		
		User user = this.userInputDtoToUser(userInputDto);
		
		if (userRepository.existsByEmailId(userInputDto.getEmailId())) {
            throw new IllegalArgumentException("UserEmailId already exists!");
        }
		
		user.setPassword(this.passwordEncoder.encode(userInputDto.getPassword()));
		
		Roles role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User savedUser = this.userRepository.save(user);
		
		return this.userToUserOutputDto(savedUser);
	}

	@Override
	public UserOutputDto updateUser(UserInputDto userInputDto, Integer userId) {
		
		if (userRepository.existsByEmailId(userInputDto.getEmailId())) {
            throw new IllegalArgumentException("UserEmailId already exists!");
        }
		
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , userId));
		
		user.setName(userInputDto.getName());
		user.setPassword(this.passwordEncoder.encode(userInputDto.getPassword()));
		user.setEmailId(userInputDto.getEmailId());
		user.setAbout(userInputDto.getAbout());
		
		User updatedUser = this.userRepository.save(user);
		
		return this.userToUserOutputDto(updatedUser);
	}

	@Override
	public UserOutputDto getUserById(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , userId));
	
		return this.userToUserOutputDto(user);
	}

}
