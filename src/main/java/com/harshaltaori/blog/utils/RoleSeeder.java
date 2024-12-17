package com.harshaltaori.blog.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.harshaltaori.blog.configs.AppConstants;
import com.harshaltaori.blog.models.Roles;
import com.harshaltaori.blog.repositories.RoleRepository;

@Component
public class RoleSeeder implements CommandLineRunner {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
				Roles role = new Roles();
				role.setRoleId(AppConstants.ADMIN_USER);
				role.setRole("ROLE_ADMIN");
				
				Roles role1 = new Roles();
				role1.setRoleId(AppConstants.NORMAL_USER);
				role1.setRole("ROLE_USER");
				
				List<Roles> roles = List.of(role, role1);
				List<Roles> result = this.roleRepository.saveAll(roles);
				System.out.println(result);
		
	}

}
