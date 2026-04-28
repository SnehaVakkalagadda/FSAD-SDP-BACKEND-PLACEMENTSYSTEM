package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.entity.Student;
import com.klef.fsad.sdp.placementsystem.repository.ApplicationRepository;
import com.klef.fsad.sdp.placementsystem.repository.JobRepository;
import com.klef.fsad.sdp.placementsystem.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
    private StudentRepository studentRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Student verifyStudentLogin(String email, String pwd) 
    {
        return studentRepository.findByEmailAndPassword(email, pwd);
    }
    
    public String studentRegistration(Student student) 
    {
    	String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);

        studentRepository.save(student);
        return "Student Registered Successfully";
    }
    
//    @Override
//    public String verifyStudentLogin(String email, String pwd) 
//    {
//        Student s = studentRepository.findByEmailAndPassword(email, pwd);
//
//        if(s != null)
//        {
//            otpService.generateOtp(email); // send OTP
//            return "OTP Sent to Email";
//        }
//        else
//        {
//            return "Invalid Credentials";
//        }
//    }
//    
//    public String verifyStudentOtp(String email, String otp)
//    {
//        boolean valid = otpService.verifyOtp(email, otp);
//
//        if(valid)
//        {
//            return "Login Successful";
//        }
//        else
//        {
//            return "Invalid OTP";
//        }
//    }
    
    public String updateStudentProfile(Student student) 
    {
        Optional<Student> optional = studentRepository.findById(student.getId());
        if(optional.isPresent())
        {
            Student s = optional.get();
            s.setName(student.getName());
            s.setEmail(student.getEmail());
            //s.setPassword(student.getPassword());
            s.setBranch(student.getBranch());
            //s.setResume(student.getResume());
            s.setUsername(student.getUsername());
            s.setCgpa(student.getCgpa());
            s.setYear(student.getYear());
            s.setCollegeName(student.getCollegeName());
            s.setContact(student.getContact());
            
            if (student.getPassword() != null && !student.getPassword().isEmpty()) 
            {
                s.setPassword(passwordEncoder.encode(student.getPassword()));
            }
            studentRepository.save(s);
            return "Student Profile Updated Successfully";
        }
        else
        {
            return "Student ID Not Found";
        }
    }
    
    public List<Job> viewJobs() 
    {
        return jobRepository.findAll();
    }
    
    public String applyJob(ApplicationDTO dto) 
    {
    	Optional<Job> optional = jobRepository.findById(dto.getJobId());
        if(optional.isPresent())
        {
        	Application app = new Application();

            app.setStudentId(dto.getStudentId());
            app.setJobId(dto.getJobId());
            app.setStatus("APPLIED");

            applicationRepository.save(app);

            return "Applied Successfully";
        }
        else
        {
            return "Job ID Not Found";
        }
    }
    
    public List<Application> trackApplications(int studentId) 
    {
        return applicationRepository.findByStudentId(studentId);
    }
}