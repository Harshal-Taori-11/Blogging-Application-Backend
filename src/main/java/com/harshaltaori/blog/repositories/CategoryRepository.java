package com.harshaltaori.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshaltaori.blog.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
