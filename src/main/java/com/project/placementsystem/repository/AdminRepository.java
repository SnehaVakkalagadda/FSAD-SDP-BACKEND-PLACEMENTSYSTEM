package com.project.placementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.placementsystem.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
	Admin findByUsernameAndPassword(String username, String password);
}