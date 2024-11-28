package com.harshaltaori.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	
	@NotBlank
	@Size(max = 20, message = "Category name should be less than 20 characters." )
	private String categoryName;
	
	@NotBlank
	@Size(min = 10, message = "Describe the category more.")
	private String categoryDescription;
}
