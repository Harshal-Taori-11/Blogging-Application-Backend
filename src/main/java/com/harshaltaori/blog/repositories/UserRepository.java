package com.harshaltaori.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshaltaori.blog.models.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	boolean existsByName(String name);
	
	Optional<User> findByEmailId(String emailId);

}
