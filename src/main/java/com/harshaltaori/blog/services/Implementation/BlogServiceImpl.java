package com.harshaltaori.blog.services.Implementation;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Category;
import com.harshaltaori.blog.payloads.BlogDto;
import com.harshaltaori.blog.repositories.BlogRepository;
import com.harshaltaori.blog.services.BlogService;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	private Blog blogDtoToBlog(BlogDto blogDto) {
		return modelMapper.map(blogDto,Blog.class);
	}
	
	private BlogDto blogToBlogDto(Blog blog) {
		return modelMapper.map(blog, BlogDto.class);
	}
	
	
	@Override
	public Blog createBlog(BlogDto blogDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blog updateBlog(BlogDto blogDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBlog(Integer blogId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Blog> getAllBlog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blog getBlog(Integer blogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Blog> getAllBlogsByUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Blog> getAllBlogsByCategories(List<Category> categoryIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Blog> searchBlogs(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
