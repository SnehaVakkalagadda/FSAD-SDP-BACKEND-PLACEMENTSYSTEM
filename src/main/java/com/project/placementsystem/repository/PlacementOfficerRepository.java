package com.project.placementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.placementsystem.entity.PlacementOfficer;

public interface PlacementOfficerRepository extends JpaRepository<PlacementOfficer, Integer>{
	PlacementOfficer findByEmailAndPassword(String email, String Password);
	
	@Query("Select p from PlacementOfficer p where p.email=:mail or p.username=:uname")
	Optional<PlacementOfficer> getOfficerByEmailOrUsername(@Param("mail") String  email, @Param("uname") String Username);
	
	Optional<PlacementOfficer> findByEmail(String email);
}