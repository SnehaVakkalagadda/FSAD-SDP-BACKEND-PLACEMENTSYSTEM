package com.project.placementsystem.service;

import java.util.List;

import com.project.placementsystem.dto.ApplicationDTO;
import com.project.placementsystem.entity.Application;
import com.project.placementsystem.entity.Job;
import com.project.placementsystem.entity.Student;

public interface StudentService {
	public Student verifyStudentLogin(String email, String pwd);
    public String studentRegistration(Student student);
    public String updateStudentProfile(Student student);
    public List<Job> viewJobs();
    public String applyJob(ApplicationDTO dto);
    public List<Application> trackApplications(int studentId);
}