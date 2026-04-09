package com.project.placementsystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
	public Object getUserByLogin(String input);
}