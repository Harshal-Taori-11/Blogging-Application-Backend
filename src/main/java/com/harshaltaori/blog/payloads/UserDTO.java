package com.harshaltaori.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	
	private int userId;
	
	@NotNull
	@Size(min = 4,message = "Username must be minimum of 4 characters.")
	private String userName;
	
	@Email(message = "Email address not Valid!")
	private String emailId;
	
	@NotNull
	@Size(min = 8,message = "Password should have at least 8 characters")
	private String password;
	
	@NotNull
	@Size(min = 15, message = "Provide more information avout you.")
	private String about;
	
}
