package com.harshaltaori.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.harshaltaori.blog.payloads.BlogOutputDto;

public interface FileService {
	
	BlogOutputDto uploadImage(Integer blogId, MultipartFile image) throws IOException;
	
	InputStream getBlogImage(Integer blogId) throws FileNotFoundException;

}
