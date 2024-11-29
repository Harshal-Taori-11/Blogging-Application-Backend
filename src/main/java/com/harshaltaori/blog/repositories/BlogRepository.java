package com.harshaltaori.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.models.Category;



public interface BlogRepository extends JpaRepository<Blog, Integer> {
	
	List<Blog> findByUser(User user);
	List<Blog> findByCategories(List<Category> categories);
}
