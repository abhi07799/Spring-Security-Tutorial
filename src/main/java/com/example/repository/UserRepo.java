package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>
{
	UserEntity findByUserName(String name);
	
	boolean existsByUserName(String name);
}
