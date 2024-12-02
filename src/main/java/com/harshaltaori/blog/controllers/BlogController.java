package com.harshaltaori.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshaltaori.blog.payloads.ApiResponse;
import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;
import com.harshaltaori.blog.services.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@PostMapping("/")
	public ResponseEntity<BlogOutputDto> createBlog(@RequestBody BlogInputDto blogInputDto){
		
		BlogOutputDto blogOutputDto = this.blogService.createBlog(blogInputDto);
		
		return new ResponseEntity<BlogOutputDto>(blogOutputDto,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{blogId}")
	public ResponseEntity<BlogOutputDto> getBlog(@PathVariable Integer blogId){
		
		BlogOutputDto blogOutputDto = this.blogService.getBlog(blogId);
		return new ResponseEntity<BlogOutputDto>(blogOutputDto,HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<BlogOutputDto>> getAllBlogsByUser(){
		
		List<BlogOutputDto> blogOutputDtos = this.blogService.getAllBlogs();
		return new ResponseEntity<List<BlogOutputDto>>(blogOutputDtos,HttpStatus.OK);
	}
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<BlogOutputDto>> getAllBlogsByUser(@PathVariable Integer userId){
		
		List<BlogOutputDto> blogOutputDtos = this.blogService.getAllBlogsByUser(userId);
		return new ResponseEntity<List<BlogOutputDto>>(blogOutputDtos,HttpStatus.OK);
	}
	
	
	@GetMapping("/categories")
	public ResponseEntity<List<BlogOutputDto>> getAllBlogsByCategories(@RequestBody List<Integer> categoryIds){
		
		List<BlogOutputDto> blogOutputDtos = this.blogService.getAllBlogByCategories(categoryIds);
		return new ResponseEntity<List<BlogOutputDto>>(blogOutputDtos,HttpStatus.OK);
	}
	
	
	@PutMapping("/{blogId}")
	public ResponseEntity<BlogOutputDto> updateBlog(@RequestBody BlogInputDto blogInputDto ,@PathVariable Integer blogId){
		
		BlogOutputDto blogOutputDto = this.blogService.updateBlog(blogInputDto, blogId);
		return new ResponseEntity<BlogOutputDto>(blogOutputDto,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{blogId}")
	public ResponseEntity<ApiResponse> deleteBlog(@PathVariable Integer blogId){
		
		this.blogService.deleteBlog(blogId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Blog deleted successfully",true),HttpStatus.OK);
	}
	
	
}
