package com.harshaltaori.blog.services;

import java.util.List;

import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Category;
import com.harshaltaori.blog.payloads.BlogDto;

public interface BlogService {
	
	Blog createBlog(BlogDto blogDto);
	
	Blog updateBlog(BlogDto blogDto);
	
	void deleteBlog(Integer blogId);
	
	List<Blog> getAllBlog();
	
	Blog getBlog(Integer blogId);
	
	List<Blog> getAllBlogsByUser(Integer userId);
	
	List<Blog> getAllBlogsByCategories(List<Category> categoryIds);
	
	List<Blog> searchBlogs(String keyword);
	
}
