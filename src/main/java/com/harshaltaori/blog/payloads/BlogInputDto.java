package com.harshaltaori.blog.payloads;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BlogInputDto {
	
	private String blogTitle;
	private String blogContent;
	private String imageUrl;
	private Integer user_id;
	private Set<Integer> category_ids;
}
