package com.harshaltaori.blog.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Category;
import com.harshaltaori.blog.models.User;

public interface BlogRepository extends JpaRepository<Blog, Integer>{
	
	List<Blog> findByUser(User user);
	
	@Query("SELECT b FROM Blog b JOIN b.categories c WHERE c.categoryId IN :categoryIds")
	List<Blog> findByCategories(@Param("categoryIds") List<Integer> categoryIds);
	
	List<Blog> findByBlogTitleContaining(String keyword);
	
	
	//For Pagination and sorting
	Page<Blog> findByCategoriesIn(Set<Category> categories,Pageable pageable);
	
	Page<Blog> findByUser(User user, Pageable pageable);
	
	Page<Blog> findByBlogTitleContainingOrBlogContentContaining(String keyword,String contentKeyword,Pageable pageable);
	
	
	//
	
	@Query("SELECT b FROM Blog b WHERE b.currentStatus = 'Approved'")
	Page<Blog> findAllApprovedBlogs(Pageable pageable);

}
