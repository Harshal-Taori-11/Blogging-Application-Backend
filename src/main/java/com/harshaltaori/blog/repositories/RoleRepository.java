package com.harshaltaori.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshaltaori.blog.models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer>{

}
