package com.klef.fsad.sdp.placementsystem.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
//    public String studentRegistration(Student student) 
//    {
//    	String encodedPassword = passwordEncoder.encode(student.getPassword());
//        student.setPassword(encodedPassword);
//
//        studentRepository.save(student);
//        return "Student Registered Successfully";
//    }
    
    @Override
    public String studentRegistration(String name, String email, String password,
            String branch, float cgpa, int year,
            String username, String collegeName, String contact,
            MultipartFile file) 
    {
        try
        {
            //  FILE VALIDATION
            if(file == null || file.isEmpty())
                return "Resume is required";

            if(!file.getContentType().equals("application/pdf"))
                return "Only PDF files allowed";

            if(file.getSize() > 2 * 1024 * 1024)
                return "File size must be less than 2MB";

            //  SAVE FILE
            String folder = System.getProperty("user.dir") + "/uploads/";

            File dir = new File(folder);

            if (!dir.exists()) 
            {
                boolean created = dir.mkdirs();
                System.out.println("Uploads folder created: " + created);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File("uploads/" + fileName);
            file.transferTo(dest);

            // SAVE STUDENT
            Student s = new Student();

            s.setName(name);
            s.setEmail(email);
            s.setPassword(passwordEncoder.encode(password));
            s.setBranch(branch);
            s.setCgpa(cgpa);
            s.setYear(year);
            s.setUsername(username);
            s.setCollegeName(collegeName);
            s.setContact(contact);
            s.setResume(folder + fileName);

            studentRepository.save(s);

            return "Student Registered Successfully";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Error in Registration";
        }
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
    
//    public String updateStudentProfile(Student student) 
//    {
//        Optional<Student> optional = studentRepository.findById(student.getId());
//        if(optional.isPresent())
//        {
//            Student s = optional.get();
//            s.setName(student.getName());
//            s.setEmail(student.getEmail());
//            //s.setPassword(student.getPassword());
//            s.setBranch(student.getBranch());
//            //s.setResume(student.getResume());
//            s.setUsername(student.getUsername());
//            s.setCgpa(student.getCgpa());
//            s.setYear(student.getYear());
//            s.setCollegeName(student.getCollegeName());
//            s.setContact(student.getContact());
//            
//            if (student.getPassword() != null && !student.getPassword().isEmpty()) 
//            {
//                s.setPassword(passwordEncoder.encode(student.getPassword()));
//            }
//            studentRepository.save(s);
//            return "Student Profile Updated Successfully";
//        }
//        else
//        {
//            return "Student ID Not Found";
//        }
//    }
    
    @Override
    public String updateStudentProfile(int id, String name, String email,
            String branch, float cgpa, int year,
            String username, String collegeName, String contact,
            MultipartFile file) 
    {
        try
        {
            Optional<Student> optional = studentRepository.findById(id);

            if(optional.isEmpty())
                return "Student Not Found";

            Student s = optional.get();

            s.setName(name);
            s.setEmail(email);
            s.setBranch(branch);
            s.setCgpa(cgpa);
            s.setYear(year);
            s.setUsername(username);
            s.setCollegeName(collegeName);
            s.setContact(contact);

            //  IF FILE PROVIDED
            if(file != null && !file.isEmpty())
            {
                if(!file.getContentType().equals("application/pdf"))
                    return "Only PDF allowed";

                if(file.getSize() > 2 * 1024 * 1024)
                    return "File size > 2MB";

                String folder = System.getProperty("user.dir") + "/uploads/";

                File dir = new File(folder);

                if (!dir.exists())
                {
                    boolean created = dir.mkdirs();
                    System.out.println("Uploads folder created: " + created);
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                File dest = new File(folder + fileName);

                file.transferTo(dest);

                //  STORE RELATIVE PATH
                s.setResume("uploads/" + fileName);
            }

            studentRepository.save(s);

            return "Profile Updated Successfully";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Error updating profile";
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