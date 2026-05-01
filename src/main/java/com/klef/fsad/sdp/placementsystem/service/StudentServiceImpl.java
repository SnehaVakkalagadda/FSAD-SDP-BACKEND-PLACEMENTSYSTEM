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

    @Override
    public Student verifyStudentLogin(String email, String pwd) {
        return studentRepository.findByEmailAndPassword(email, pwd);
    }

    @Override
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
            MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                return "Resume is required";
            }

            if (!"application/pdf".equals(file.getContentType())) {
                return "Only PDF files allowed";
            }

            if (file.getSize() > 2 * 1024 * 1024) {
                return "File size must be less than 2MB";
            }

            String folder = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
            File dir = new File(folder);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(folder + fileName);
            file.transferTo(dest);

            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(passwordEncoder.encode(password));
            student.setBranch(branch);
            student.setCgpa(cgpa);
            student.setYear(year);
            student.setUsername(username);
            student.setCollegeName(collegeName);
            student.setContact(contact);
            student.setResume("uploads/" + fileName);

            studentRepository.save(student);

            return "Student Registered Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in Registration";
        }
    }

    @Override
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
            MultipartFile file) {

        try {
            Optional<Student> optional = studentRepository.findById(id);

            if (optional.isEmpty()) {
                return "Student Not Found";
            }

            Student student = optional.get();

            student.setName(name);
            student.setEmail(email);
            student.setBranch(branch);
            student.setCgpa(cgpa);
            student.setYear(year);
            student.setUsername(username);
            student.setCollegeName(collegeName);
            student.setContact(contact);

            if (file != null && !file.isEmpty()) {
                if (!"application/pdf".equals(file.getContentType())) {
                    return "Only PDF allowed";
                }

                if (file.getSize() > 2 * 1024 * 1024) {
                    return "File size must be less than 2MB";
                }

                String folder = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
                File dir = new File(folder);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File dest = new File(folder + fileName);
                file.transferTo(dest);

                student.setResume("uploads/" + fileName);
            }

            studentRepository.save(student);

            return "Profile Updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating profile";
        }
    }

    @Override
    public List<Job> viewJobs() {
        return jobRepository.findAll();
    }

    @Override
    public String applyJob(ApplicationDTO dto) {
        Optional<Job> optional = jobRepository.findById(dto.getJobId());

        if (optional.isEmpty()) {
            return "Job ID Not Found";
        }

        Application app = new Application();
        app.setStudentId(dto.getStudentId());
        app.setJobId(dto.getJobId());
        app.setStatus("APPLIED");

        applicationRepository.save(app);

        return "Applied Successfully";
    }

    @Override
    public List<Application> trackApplications(int studentId) {
        return applicationRepository.findByStudentId(studentId);
    }
}