package com.project.placementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.placementsystem.entity.Application;
import com.project.placementsystem.entity.PlacementOfficer;
import com.project.placementsystem.repository.ApplicationRepository;
import com.project.placementsystem.repository.PlacementOfficerRepository;

@Service
public class PlacementOfficerServiceImpl implements PlacementOfficerService {
	@Autowired
    private PlacementOfficerRepository officerRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Override
    public PlacementOfficer verifyOfficerLogin(String email, String pwd) 
    {
        return officerRepository.findByEmailAndPassword(email, pwd);
    }

    @Override
    public List<Application> getAllApplications() 
    {
        return applicationRepository.findAll();
    }

    @Override
    public String updateApplicationStatus(int applicationId, String status, int studentId) 
    {
        Optional<Application> optional = applicationRepository.findByStudentIdAndAppId(studentId,applicationId);
        if(optional.isPresent())
        {
            Application app = optional.get();
            app.setStatus(status.toUpperCase());
            applicationRepository.save(app);
            return "Application Status Updated Successfully";
        }
        else
        {
            return "Application ID Not Found";
        }
    }
    
}