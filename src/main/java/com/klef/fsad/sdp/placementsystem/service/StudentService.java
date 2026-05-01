package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.entity.Student;

public interface StudentService {

    public Student verifyStudentLogin(String email, String pwd);

    // Registration with file upload (latest version)
    public String studentRegistration(
            String name,
            String email,
            String password,
            String branch,
            float cgpa,
            int year,
            String username,
            String collegeName,
            String contact,
            MultipartFile file
    );

    // Update profile with file upload
    public String updateStudentProfile(
            int id,
            String name,
            String email,
            String branch,
            float cgpa,
            int year,
            String username,
            String collegeName,
            String contact,
            MultipartFile file
    );

    public List<Job> viewJobs();

    public String applyJob(ApplicationDTO dto);

    public List<Application> trackApplications(int studentId);
}