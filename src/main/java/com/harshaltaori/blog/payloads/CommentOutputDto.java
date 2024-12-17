package com.harshaltaori.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentOutputDto {
	
	private Integer commentId;
	private String comment;
	private UserOutputDto user;
}
