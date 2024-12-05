package com.harshaltaori.blog.services.Implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harshaltaori.blog.exceptions.ResourceNotFoundException;
import com.harshaltaori.blog.models.Blog;
import com.harshaltaori.blog.payloads.BlogInputDto;
import com.harshaltaori.blog.payloads.BlogOutputDto;
import com.harshaltaori.blog.repositories.BlogRepository;
import com.harshaltaori.blog.services.BlogService;
import com.harshaltaori.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${project.image}")
	private String path;
	
	
	@Override
	public BlogOutputDto uploadImage(Integer blogId, MultipartFile image) throws IOException {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", blogId));
		
		
		 String fileName = image.getOriginalFilename();
		 if (fileName == null || fileName.isEmpty()) {
			 throw new IllegalArgumentException("File name is invalid.");
		 }
		
		String randomId = UUID.randomUUID().toString();
		
		
		String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
		String filePath = path + File.separator +newFileName;
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		
		Files.copy(image.getInputStream(),Paths.get(filePath));
		
		blog.setImageUrl(newFileName);
		blog.setLastUpdatedOn(new Date());
		Blog blogOut = blogRepository.save(blog);
		
		return this.modelMapper.map(blogOut, BlogOutputDto.class);
	}

	@Override
	public InputStream getBlogImage(Integer blogId) throws FileNotFoundException {
		
		Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", blogId));
		
		String fullPath = path + File.separator + blog.getImageUrl();
		InputStream io = new FileInputStream(fullPath);
		
		return io;
		
	}

}
