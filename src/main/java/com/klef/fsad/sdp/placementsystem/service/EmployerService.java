package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;

import com.klef.fsad.sdp.placementsystem.dto.JobDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.Job;

public interface EmployerService {
	    public Employer verifyEmployerLogin(String email, String pwd);
	    public String employerRegistration(Employer employer);
	    public String updateEmployerProfile(Employer employer);
	    public String postJob(JobDTO dto);
	    public List<Job> viewJobsByEmployer(int employerId);
	    public List<Application> viewApplicationsByJob(int jobId);
}