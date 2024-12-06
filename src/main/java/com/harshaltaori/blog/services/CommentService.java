package com.harshaltaori.blog.services;

import java.util.List;

import com.harshaltaori.blog.payloads.CommentDto;
import com.harshaltaori.blog.payloads.CommentOutputDto;

public interface CommentService {
	
	CommentOutputDto createComment(CommentDto commentDto,Integer blogId,Integer userId);
	
	void deleteComment(Integer commentId);
	
	List<CommentOutputDto> getAllCommentsByBlog(Integer blogId);
}
