package com.harshaltaori.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInputDto {

private int userId;
	
	@NotBlank
	@Size(min = 4,message = "Username must be minimum of 4 characters.")
	private String name;
	
	@Email(message = "Email address not Valid!")
	private String emailId;
	
	@NotBlank
	@Size(min = 8,message = "Password should have at least 8 characters")
	private String password;
	
	@NotBlank
	@Size(min = 15, message = "Provide more information avout you.")
	private String about;
}
