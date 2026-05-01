package com.klef.fsad.sdp.placementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.fsad.sdp.placementsystem.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
	List<Job> findByEmployerId(int employerId);
}