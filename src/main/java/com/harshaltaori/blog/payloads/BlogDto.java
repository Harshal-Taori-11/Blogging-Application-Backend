package com.harshaltaori.blog.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BlogDto {
	
	private String blogTitle;
	
	private String blogContent;
	
	private String imageUrl;
	
	private Date dateLastUpdated;
	
	private Date dateAdded;
	
	
}
