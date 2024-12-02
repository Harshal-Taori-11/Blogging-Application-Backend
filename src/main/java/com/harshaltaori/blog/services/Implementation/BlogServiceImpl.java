package com.harshaltaori.blog.services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.exceptions.ResourceNotFoundException;
import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Category;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;
import com.harshaltaori.blog.repositories.BlogRepository;
import com.harshaltaori.blog.repositories.CategoryRepository;
import com.harshaltaori.blog.repositories.UserRepository;
import com.harshaltaori.blog.services.BlogService;

@Service
public class BlogServiceImpl implements BlogService {
	
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private Blog blogInputDtoToBlog(BlogInputDto blogInputDto) {
		return modelMapper.map(blogInputDto, Blog.class);
	}
	
	
	private BlogOutputDto blogtoBlogOutputDto(Blog blog) {
		return modelMapper.map(blog, BlogOutputDto.class);
	}
	

	@Override
	public BlogOutputDto createBlog(BlogInputDto blogInputDto) {
		
		Blog blog = this.blogInputDtoToBlog(blogInputDto);
		
		User user = this.userRepository.findById(blogInputDto.getUser_id()).orElseThrow(()-> new ResourceNotFoundException("User", blogInputDto.getUser_id()));
		blog.setUser(user);
		
		List<Category> listCategories = this.categoryRepository.findAllById(blogInputDto.getCategory_ids());
		Set<Category> categories = listCategories.stream().collect(Collectors.toSet());
		if(categories.size() > 5  || categories.size() < 1) {
			throw new IllegalArgumentException("Total number of categories should be minimum of 1 and maximum of 5");
		}
		blog.setCategories(categories);
		
		blog.setAddedOn(new Date());
		blog.setLastUpdatedOn(new Date());
		
		
		Blog createdBlog = this.blogRepository.save(blog); 
		
		
		return this.blogtoBlogOutputDto(createdBlog);
	}

	@Override
	public BlogOutputDto updateBlog(BlogInputDto blogInputDto, Integer blogId) {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(()-> new ResourceNotFoundException("Blog", blogId));
		
		List<Category> listCategories = this.categoryRepository.findAllById(blogInputDto.getCategory_ids());
		Set<Category> categories = listCategories.stream().collect(Collectors.toSet());
		if(categories.size() > 5  || categories.size() < 1) {
			throw new IllegalArgumentException("Total number of categories should be minimum of 1 and maximum of 5");
		}
		blog.setCategories(categories);
		blog.setBlogTitle(blogInputDto.getBlogTitle());
		blog.setBlogContent(blogInputDto.getBlogContent());
		blog.setImageUrl(blogInputDto.getImageUrl());
		blog.setLastUpdatedOn(new Date());
		
		Blog updatedBlog = this.blogRepository.save(blog); 
		
		
		return this.blogtoBlogOutputDto(updatedBlog);
	}

	@Override
	public void deleteBlog(Integer blogId) {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(()-> new ResourceNotFoundException("Blog", blogId));
		
		this.blogRepository.delete(blog);
	}

	@Override
	public BlogOutputDto getBlog(Integer blogId) {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(()-> new ResourceNotFoundException("Blog", blogId));
		
		return this.blogtoBlogOutputDto(blog);
	}

	@Override
	public List<BlogOutputDto> getAllBlogs() {
		
		List<Blog> blogs = this.blogRepository.findAll();
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		
		return blogOutputDtos;
	}

	@Override
	public List<BlogOutputDto> getAllBlogsByUser(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", userId));
		
		List<Blog> blogs = this.blogRepository.findByUser(user);
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		
		return blogOutputDtos;
	}

	@Override
	public List<BlogOutputDto> getAllBlogByCategories(List<Integer> categoryIds) {
		
//		List<Category> listCategories = this.categoryRepository.findAllById(categoryIds);
//		Set<Category> categories = listCategories.stream().collect(Collectors.toSet());
		
		List<Blog> blogs = this.blogRepository.findByCategories(categoryIds);
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		
		return blogOutputDtos;
	}

	@Override
	public List<BlogOutputDto> getAllBlogTitlesByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
