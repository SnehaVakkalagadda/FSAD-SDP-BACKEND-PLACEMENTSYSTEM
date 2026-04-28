package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.placementsystem.dto.JobDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.repository.ApplicationRepository;
import com.klef.fsad.sdp.placementsystem.repository.EmployerRepository;
import com.klef.fsad.sdp.placementsystem.repository.JobRepository;

@Service
public class EmployerServiceImpl implements EmployerService {
	@Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Employer verifyEmployerLogin(String email, String pwd) 
    {
        return employerRepository.findByEmailAndPassword(email, pwd);
    }

    @Override
    public String employerRegistration(Employer employer) 
    {
    	String encodedPassword = passwordEncoder.encode(employer.getPassword());
        employer.setPassword(encodedPassword);

        employerRepository.save(employer);
        return "Employer Registered Successfully";
    }

    @Override
    public String updateEmployerProfile(Employer employer) 
    {
        Optional<Employer> optional = employerRepository.findById(employer.getId());

        if(optional.isPresent())
        {
            Employer e = optional.get();

            e.setCompanyName(employer.getCompanyName());
            e.setEmail(employer.getEmail());
            e.setCompanyMail(employer.getCompanyMail());
            e.setUsername(employer.getUsername());
            e.setLocation(employer.getLocation());
            e.setContact(employer.getContact());
            
            if (employer.getPassword() != null && !employer.getPassword().isEmpty()) 
            {
                e.setPassword(passwordEncoder.encode(employer.getPassword()));
            }

            employerRepository.save(e);

            return "Employer Profile Updated Successfully";
        }
        else
        {
            return "Employer ID Not Found";
        }
    }

    @Override
    public String postJob(JobDTO dto) 
    {
        Job job = new Job();

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setSalary(dto.getSalary());
        job.setEmployerId(dto.getEmployerId());

        jobRepository.save(job);

        return "Job Posted Successfully";
    }

    public List<Job> viewJobsByEmployer(int employerId) 
    {
        return jobRepository.findByEmployerId(employerId);
    }

    @Override
    public List<Application> viewApplicationsByJob(int jobId) 
    {
        return applicationRepository.findByJobId(jobId);
    }
}