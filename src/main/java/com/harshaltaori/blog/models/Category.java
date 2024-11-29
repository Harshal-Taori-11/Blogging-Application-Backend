package com.harshaltaori.blog.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int categoryId;
	
	@Column(name = "name",nullable = false,length = 100)
	private String categoryName;
	
	@Column(name = "description")
	private String categoryDescription;
	
	@ManyToMany(mappedBy = "categories")
	private List<Blog> blogs = new ArrayList<>();
}
