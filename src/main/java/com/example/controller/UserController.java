package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserEntity;
import com.example.repository.UserRepo;

@RestController
public class UserController
{
	@Autowired
	private UserRepo repo;
	
	@GetMapping("public/test")
	public String test1()
	{
		return "JAI SHREE RAM";
	}
	
	@PreAuthorize("hasRole('USER')")  /*<-- if we want to provide role based authentication by in memory authentication then first uncomment this annotation and than go to SpringSecurityConfig.java and follow next steps*/
	@GetMapping("user/test")
	public String test2()
	{
		return "JAI BAJRANG BALI";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admin/test")
	public String test3()
	{
		return "GANAPATI BAPPA MORAIYA";
	}
	
	@PostMapping("public/addUser")
	public UserEntity addUser(@RequestBody UserEntity user)
	{
		return repo.save(user);
	}
	
	@GetMapping("user/getUser/{id}")
	public UserEntity getUser(@PathVariable int id)
	{
		return repo.findById(id).get();
	}
	
	@GetMapping("user/updateUser")
	public String updateUser()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		if(repo.existsByUserName(username))
		{
			return "User is Present  "+username;
		}
		else
			return "Not Found";
	}
	
	@GetMapping("admin/allUsers")
	public List<UserEntity> allUsers()
	{
		return repo.findAll();
	}
}
