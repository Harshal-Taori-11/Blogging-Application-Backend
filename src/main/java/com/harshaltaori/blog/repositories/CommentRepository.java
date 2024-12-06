package com.harshaltaori.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	List<Comment> findByBlog(Blog blog);
}
