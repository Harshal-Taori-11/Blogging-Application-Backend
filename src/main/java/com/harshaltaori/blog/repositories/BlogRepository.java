package com.harshaltaori.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.models.Category;
import java.util.Set;


public interface BlogRepository extends JpaRepository<Blog, Integer>{
	
	List<Blog> findByUser(User user);
	
	@Query("SELECT b FROM Blog b JOIN b.categories c WHERE c.categoryId IN :categoryIds")
	List<Blog> findByCategories(@Param("categoryIds") List<Integer> categoryIds);
	
	List<Blog> findByBlogTitleContaining(String keyword);
	
}
