package com.klef.fsad.sdp.placementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.fsad.sdp.placementsystem.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
	Admin findByUsernameAndPassword(String username, String password);
}