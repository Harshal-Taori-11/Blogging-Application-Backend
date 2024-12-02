package com.harshaltaori.blog.services;

import java.util.List;

import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;

public interface BlogService {
	
	BlogOutputDto createBlog(BlogInputDto blogInputDto);
	
	BlogOutputDto updateBlog(BlogInputDto blogInputDto,Integer blogId);
	
	void deleteBlog(Integer blogId);
	
	BlogOutputDto getBlog(Integer blogId);
	
	List<BlogOutputDto> getAllBlogs();
	
	List<BlogOutputDto> getAllBlogsByUser(Integer userId);
	
	List<BlogOutputDto> getAllBlogByCategories(List<Integer> categoryIds);
	
	List<BlogOutputDto> getAllBlogTitlesByKeyword(String keyword);
}
