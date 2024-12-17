package com.harshaltaori.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshaltaori.blog.payloads.ApiResponse;
import com.harshaltaori.blog.payloads.CommentDto;
import com.harshaltaori.blog.payloads.CommentOutputDto;
import com.harshaltaori.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/blog/{blogId}")
	public ResponseEntity<List<CommentOutputDto>> getAllCommentsOnABlog(@PathVariable Integer blogId){
		List<CommentOutputDto> commentOutputDtos = this.commentService.getAllCommentsByBlog(blogId);
		
		return ResponseEntity.ok(commentOutputDtos);
	}
	
	
	@PostMapping("/blog/{blogId}/user/{userId}")
	public ResponseEntity<CommentOutputDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer blogId,@PathVariable Integer userId){
		
		CommentOutputDto commentOutputDto= this.commentService.createComment(commentDto, blogId, userId);
		return ResponseEntity.ok(commentOutputDto);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		
		return ResponseEntity.ok(new ApiResponse("Comment deleted successfully",true));
	}
	
}
