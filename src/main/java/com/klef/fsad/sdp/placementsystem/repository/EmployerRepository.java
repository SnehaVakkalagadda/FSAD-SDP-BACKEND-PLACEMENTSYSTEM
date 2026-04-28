package com.klef.fsad.sdp.placementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.klef.fsad.sdp.placementsystem.entity.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {
	Employer findByEmailAndPassword(String email, String password);
	
	@Query("Select e from Employer e where e.email=:email or e.username=:username")
	Optional<Employer> getEmployerByEmailOrUsername(@Param("email")String email, @Param("username")String Username);
	
	Optional<Employer> findByEmail(String email); 
}