package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.entity.Student;

public interface StudentService {
	public Student verifyStudentLogin(String email, String pwd);
    public String studentRegistration(Student student);
    public String updateStudentProfile(Student student);
    public List<Job> viewJobs();
    public String applyJob(ApplicationDTO dto);
    public List<Application> trackApplications(int studentId);
}