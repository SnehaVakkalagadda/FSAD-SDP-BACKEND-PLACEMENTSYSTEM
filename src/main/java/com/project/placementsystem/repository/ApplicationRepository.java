package com.project.placementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.placementsystem.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	List<Application> findByStudentId(int studentId);    //with this student can track his application

    List<Application> findByJobId(int jobId);   //with this employer can see who applied
    
	@Query("Select a from Application a where a.studentId=:sid and a.id=:aid")
	Optional<Application> findByStudentIdAndAppId(@Param("sid") int sid, @Param("aid") int aid);
}