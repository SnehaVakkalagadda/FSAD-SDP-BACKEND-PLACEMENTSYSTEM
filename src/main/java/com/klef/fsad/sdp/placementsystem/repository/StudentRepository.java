package com.klef.fsad.sdp.placementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.klef.fsad.sdp.placementsystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	Student findByEmailAndPassword(String email, String pwd);
	
	@Query("Select s from Student s where s.email=:email or s.username=:username")
	Optional<Student> getStudentByEmailOrUsername(@Param("email")String email, @Param("username")String username);
	
	Optional<Student> findByEmail(String email);
	
	List<Student> findByCollegeNameAndBranchAndYear(String college, String branch, int year);
}
