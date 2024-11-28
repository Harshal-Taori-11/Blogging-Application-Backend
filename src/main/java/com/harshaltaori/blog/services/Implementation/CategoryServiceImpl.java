package com.harshaltaori.blog.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshaltaori.blog.exceptions.ResourceNotFoundException;
import com.harshaltaori.blog.models.Category;
import com.harshaltaori.blog.payloads.CategoryDto;
import com.harshaltaori.blog.repositories.CategoryRepository;
import com.harshaltaori.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	private CategoryDto categoryToCategoryDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
	
	private Category categoryDtoToCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}
	
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.categoryDtoToCategory(categoryDto);
		Category addedCategory = this.categoryRepository.save(category);
		
		return this.categoryToCategoryDto(addedCategory);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", categoryId));
		
		return categoryToCategoryDto(category);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", categoryId));
		this.categoryRepository.delete(category);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", categoryId));
		
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepository.save(category);
		
		return this.categoryToCategoryDto(updatedCategory);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> allCategories = this.categoryRepository.findAll();
		
		List<CategoryDto> allCategoryDtos = allCategories.stream().map((category) -> this.categoryToCategoryDto(category)).collect(Collectors.toList());
		
		return allCategoryDtos;
	}

}
