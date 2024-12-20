package com.harshaltaori.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.harshaltaori.blog.models.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserOutputDto {
	
	
	private int userId;
	
	@NotBlank
	@Size(min = 4,message = "Username must be minimum of 4 characters.")
	private String name;
	
	@Email(message = "Email address not Valid!")
	private String emailId;
	
	@NotBlank
	@Size(min = 15, message = "Provide more information avout you.")
	private String about;	
	
	private Set<Roles> roles = new HashSet<>();
	
}
