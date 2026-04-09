package com.project.placementsystem.service;

import java.util.List;

import com.project.placementsystem.dto.JobDTO;
import com.project.placementsystem.entity.Employer;
import com.project.placementsystem.entity.Job;
import com.project.placementsystem.entity.Application;

public interface EmployerService {
	    public Employer verifyEmployerLogin(String email, String pwd);
	    public String employerRegistration(Employer employer);
	    public String updateEmployerProfile(Employer employer);
	    public String postJob(JobDTO dto);
	    public List<Job> viewJobsByEmployer(int employerId);
	    public List<Application> viewApplicationsByJob(int jobId);
}