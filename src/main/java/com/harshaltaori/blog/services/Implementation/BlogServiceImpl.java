package com.harshaltaori.blog.services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.configs.AppConstants;
import com.harshaltaori.blog.exceptions.ResourceNotFoundException;
import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Category;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;
import com.harshaltaori.blog.payloads.BlogResponse;
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
	
	
	private BlogResponse BlogPagesToBlogResponse(Page<Blog> blogPages) {
		return modelMapper.map(blogPages, BlogResponse.class);
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
		blog.setCurrentStatus("Pending");
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
		blog.setCurrentStatus("Pending");
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
		
		if(blog.getCurrentStatus() == "Pending") {
			throw new IllegalArgumentException("The blog is in pending state.");
		}
		
		return this.blogtoBlogOutputDto(blog);
	}

	@Override
	public BlogResponse getAllBlogs(Integer pageNumber,Integer pageSize,String sortBy, String sortDirection) {
		
//		Without Pagination and sorting
		
//		List<Blog> blogs = this.blogRepository.findAll();
		
		
//     With pagination and sorting
		
		if(pageNumber<0) {
			pageNumber = Integer.valueOf(AppConstants.PAGE_NUMBER);
		}
		
		if(pageSize<0) {
			pageSize = Integer.valueOf(AppConstants.PAGE_SIZE);
		}
		
		
		Sort sort = sortDirection.equalsIgnoreCase("Ascending")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Blog> blogPages = this.blogRepository.findAllApprovedBlogs(pageable);
//		Page<Blog> blogPages= this.blogRepository.findAll(pageable);
		List<Blog> blogs = blogPages.getContent();
		
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		
		BlogResponse blogResponse = new BlogResponse();
		
		blogResponse.setBlogOutputDtos(blogOutputDtos);
		blogResponse.setPageNumber(blogPages.getNumber());
		blogResponse.setPageSize(blogPages.getSize());
		blogResponse.setTotalElements(blogPages.getTotalElements());
		blogResponse.setTotalPages(blogPages.getTotalPages());
		blogResponse.setLast(blogPages.isLast()); 
		
		return blogResponse;
	}
	

//	Without Pagination
	
//	@Override
//	public List<BlogOutputDto> getAllBlogsByUser(Integer userId) {
//		
//		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", userId));
//		
//		List<Blog> blogs = this.blogRepository.findByUser(user);
//		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
//		
//		return blogOutputDtos;
//	}

//  With Pagination
	
	@Override
	public BlogResponse getAllBlogsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", userId));
		
		Sort sort = sortDirection.equalsIgnoreCase("Ascending")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Blog> blogsPage = this.blogRepository.findByUser(user, pageable);
		
		BlogResponse blogResponse = this.BlogPagesToBlogResponse(blogsPage);
		
		List<Blog> blogs = blogsPage.getContent().stream().filter(blog -> "Approved".equals(blog.getCurrentStatus()))
                .collect(Collectors.toList());;
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		blogResponse.setBlogOutputDtos(blogOutputDtos);
		
		return blogResponse;
	}
	
	
	
	
	
//	Without Pagination

//	@Override
//	public List<BlogOutputDto> getAllBlogByCategories(List<Integer> categoryIds) {
//		
//		List<Category> listCategories = this.categoryRepository.findAllById(categoryIds);
//		Set<Category> categories = listCategories.stream().collect(Collectors.toSet());
//		
//		List<Blog> blogs = this.blogRepository.findByCategories(categoryIds);
//		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
//		
//		return blogOutputDtos;
//	}
	
	
	
//  With Pagination
	@Override
	public BlogResponse getAllBlogByCategories(List<Integer> categoryIds, Integer pageNumber, Integer pageSize,
			String sortBy, String sortDirection) {
		
		List<Category> listCategories = this.categoryRepository.findAllById(categoryIds);
		Set<Category> categories = listCategories.stream().collect(Collectors.toSet());
		
		Sort sort = sortDirection.equalsIgnoreCase("Ascending")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Blog> blogsPage = this.blogRepository.findByCategoriesIn(categories, pageable);
		
		BlogResponse blogResponse = this.BlogPagesToBlogResponse(blogsPage);
		
		List<Blog> blogs = blogsPage.getContent().stream().filter(blog -> "Approved".equals(blog.getCurrentStatus()))
                .collect(Collectors.toList());;
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		blogResponse.setBlogOutputDtos(blogOutputDtos);
		
		return blogResponse;
	}

	@Override
	public List<BlogOutputDto> getAllBlogTitlesByKeyword(String keyword) {
		
		List<Blog> blogs = this.blogRepository.findByBlogTitleContaining(keyword).stream().filter(blog -> "Approved".equals(blog.getCurrentStatus()))
                .collect(Collectors.toList());
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		
		return blogOutputDtos;
	}


	@Override
	public BlogResponse getAllBlogTitleAndBlogContentBykeyword(String keyword, Integer pageNumber,
			Integer pageSize, String sortBy, String sortDirection) {
		
		
		Sort sort = sortDirection.equalsIgnoreCase("Ascending")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Blog> blogsPage = this.blogRepository.findByBlogTitleContainingOrBlogContentContaining(keyword, keyword, pageable);
		
		BlogResponse blogResponse = this.BlogPagesToBlogResponse(blogsPage);
		
		List<Blog> blogs = blogsPage.getContent().stream().filter(blog -> "Approved".equals(blog.getCurrentStatus()))
                .collect(Collectors.toList());;
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		blogResponse.setBlogOutputDtos(blogOutputDtos);
		
		return blogResponse;
	}
	
	@Override
	public BlogResponse getAllPendingBlogs(Integer pageNumber,Integer pageSize,String sortBy, String sortDirection) {
		
//		Without Pagination and sorting
		
//		List<Blog> blogs = this.blogRepository.findAll();
		
		
//     With pagination and sorting
		
		if(pageNumber<0) {
			pageNumber = Integer.valueOf(AppConstants.PAGE_NUMBER);
		}
		
		if(pageSize<0) {
			pageSize = Integer.valueOf(AppConstants.PAGE_SIZE);
		}
		
		
		Sort sort = sortDirection.equalsIgnoreCase("Ascending")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Blog> blogPages = this.blogRepository.findAllPendingBlogs(pageable);
//		Page<Blog> blogPages= this.blogRepository.findAll(pageable);
		List<Blog> blogs = blogPages.getContent();
		
		List<BlogOutputDto> blogOutputDtos = blogs.stream().map((blog)-> this.blogtoBlogOutputDto(blog)).collect(Collectors.toList());
		
		BlogResponse blogResponse = new BlogResponse();
		
		blogResponse.setBlogOutputDtos(blogOutputDtos);
		blogResponse.setPageNumber(blogPages.getNumber());
		blogResponse.setPageSize(blogPages.getSize());
		blogResponse.setTotalElements(blogPages.getTotalElements());
		blogResponse.setTotalPages(blogPages.getTotalPages());
		blogResponse.setLast(blogPages.isLast()); 
		
		return blogResponse;
	}


	@Override
	public BlogOutputDto approveBlog(Integer blogId) {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(()-> new ResourceNotFoundException("Blog", blogId));
		blog.setCurrentStatus("Approved");
		
		BlogOutputDto blogOutputDto = this.modelMapper.map(this.blogRepository.save(blog), BlogOutputDto.class);
		return blogOutputDto;
	}

}
