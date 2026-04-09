package com.project.placementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.placementsystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	Student findByEmailAndPassword(String email, String pwd);
	
	@Query("Select s from Student s where s.email=:email or s.username=:username")
	Optional<Student> getStudentByEmailOrUsername(@Param("email")String email, @Param("username")String username);
	
	Optional<Student> findByEmail(String email);
}