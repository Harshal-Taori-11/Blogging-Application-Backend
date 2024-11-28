package com.harshaltaori.blog.services;

import java.util.List;

import com.harshaltaori.blog.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Integer categoryId);
	
	void deleteCategory(Integer categoryId);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	List<CategoryDto> getAllCategories(); 
	
}
