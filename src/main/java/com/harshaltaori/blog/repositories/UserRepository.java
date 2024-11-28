package com.harshaltaori.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshaltaori.blog.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
