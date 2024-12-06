package com.harshaltaori.blog.services;

import java.util.List;

import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;
import com.harshaltaori.blog.payloads.BlogResponse;

public interface BlogService {
	
	BlogOutputDto createBlog(BlogInputDto blogInputDto);
	
	BlogOutputDto updateBlog(BlogInputDto blogInputDto,Integer blogId);
	
	void deleteBlog(Integer blogId);
	
	BlogOutputDto getBlog(Integer blogId);
	
	BlogResponse  getAllBlogs(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
	
	
//	List<BlogOutputDto> getAllBlogsByUser(Integer userId);
	BlogResponse getAllBlogsByUser (Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
	
//	List<BlogOutputDto> getAllBlogByCategories(List<Integer> categoryIds);
	BlogResponse getAllBlogByCategories(List<Integer> categoryIds,Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
	
	List<BlogOutputDto> getAllBlogTitlesByKeyword(String keyword);
	
	BlogResponse getAllBlogTitleAndBlogContentBykeyword(String keyword,Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
	
	
//  Related to approval for admin
	BlogOutputDto approveBlog(Integer blogId); 
	
	
}
