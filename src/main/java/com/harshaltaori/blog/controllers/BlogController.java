package com.harshaltaori.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harshaltaori.blog.configs.AppConstants;
import com.harshaltaori.blog.payloads.ApiResponse;
import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;
import com.harshaltaori.blog.payloads.BlogResponse;
import com.harshaltaori.blog.services.BlogService;
import com.harshaltaori.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileService fileService;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
	
	
	@GetMapping("/all/Approved")
	public ResponseEntity<BlogResponse> getAllBlogs(
			@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam (value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection
			){
		
		BlogResponse blogResponse = this.blogService.getAllBlogs(pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<BlogResponse>(blogResponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all/Pending")
	public ResponseEntity<BlogResponse> getAllPendingBlogs(
			@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam (value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection
			){
		
		BlogResponse blogResponse = this.blogService.getAllPendingBlogs(pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<BlogResponse>(blogResponse,HttpStatus.OK);
	}
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<BlogResponse> getAllBlogsByUser(@PathVariable Integer userId,
			@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam (value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection
			){
		
		BlogResponse blogResponse = this.blogService.getAllBlogsByUser(userId,pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<BlogResponse>(blogResponse,HttpStatus.OK);
	}
	
	
	@GetMapping("/categories")
	public ResponseEntity<BlogResponse> getAllBlogsByCategories(@RequestBody List<Integer> categoryIds,
			@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam (value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection
			){
		
		BlogResponse blogResponse = this.blogService.getAllBlogByCategories(categoryIds,pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<BlogResponse>(blogResponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PutMapping("/{blogId}")
	public ResponseEntity<BlogOutputDto> updateBlog(@RequestBody BlogInputDto blogInputDto ,@PathVariable Integer blogId){
		
		BlogOutputDto blogOutputDto = this.blogService.updateBlog(blogInputDto, blogId);
		return new ResponseEntity<BlogOutputDto>(blogOutputDto,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@DeleteMapping("/{blogId}")
	public ResponseEntity<ApiResponse> deleteBlog(@PathVariable Integer blogId){
		
		this.blogService.deleteBlog(blogId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Blog deleted successfully",true),HttpStatus.OK);
	}
	
	
	@GetMapping("/search/Title/{keyword}")
	public ResponseEntity<List<BlogOutputDto>> searchByKeyword(@PathVariable String keyword){
		
		List<BlogOutputDto> blogOutputDtos = this.blogService.getAllBlogTitlesByKeyword(keyword);
		
		return ResponseEntity.ok().body(blogOutputDtos);
	}
	
	@GetMapping("/search/TitleAndContent/{keyword}")
	public ResponseEntity<BlogResponse> searchByKeyword(@PathVariable String keyword,
			@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam (value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection
			){		
		
		BlogResponse blogResponse = this.blogService.getAllBlogTitleAndBlogContentBykeyword(keyword, pageNumber, pageSize, sortBy, sortDirection);
		
		return ResponseEntity.ok().body(blogResponse);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostMapping("/image/upload/{blogId}")
	public ResponseEntity<BlogOutputDto> uploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer blogId
			) throws IOException{
		
		BlogOutputDto blogOutputDto = this.fileService.uploadImage(blogId, image);
		
		return ResponseEntity.ok(blogOutputDto);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping(value = "/image/{blogId}", produces = MediaType.IMAGE_JPEG_VALUE )
	public  void getImage(@PathVariable Integer blogId,
			HttpServletResponse httpServletResponse) throws IOException {
		
		InputStream resource = this.fileService.getBlogImage(blogId);
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, httpServletResponse.getOutputStream());
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/approve/{blogId}")
	public ResponseEntity<BlogOutputDto> approveBlog(@PathVariable Integer blogId){
		
		BlogOutputDto blogOutputDto = this.blogService.approveBlog(blogId);
		return ResponseEntity.ok(blogOutputDto);
	}
	
	
}
