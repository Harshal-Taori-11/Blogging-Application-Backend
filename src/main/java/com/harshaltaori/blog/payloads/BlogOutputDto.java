package com.harshaltaori.blog.payloads;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BlogOutputDto {
	
	private String blogTitle;
	private String blogContent;
	private String imageUrl;
	private Date addedOn;
	private Date lastUpdatedOn;
	private UserDto user;
	private Set<CategoryDto> categories;
	
	
}
