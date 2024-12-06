package com.harshaltaori.blog.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.exceptions.ResourceNotFoundException;
import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.models.Comment;
import com.harshaltaori.blog.models.User;
import com.harshaltaori.blog.payloads.CommentDto;
import com.harshaltaori.blog.payloads.CommentOutputDto;
import com.harshaltaori.blog.repositories.BlogRepository;
import com.harshaltaori.blog.repositories.CommentRepository;
import com.harshaltaori.blog.repositories.UserRepository;
import com.harshaltaori.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CommentOutputDto createComment(CommentDto commentDto,Integer blogId,Integer userId) {
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog",blogId));
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User",userId));
		
		
		comment.setBlog(blog);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepository.save(comment);
		
		return this.modelMapper.map(savedComment, CommentOutputDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment= this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment",commentId));
		
		this.commentRepository.deleteById(commentId);
	}

	@Override
	public List<CommentOutputDto> getAllCommentsByBlog(Integer blogId) {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog",blogId));
		
		List<Comment> comments = this.commentRepository.findByBlog(blog);
		
		List<CommentOutputDto> commentOutputDtos= comments.stream().map((comment) -> this.modelMapper.map(comment, CommentOutputDto.class)).collect(Collectors.toList());
		
		return commentOutputDtos;
	}

}
