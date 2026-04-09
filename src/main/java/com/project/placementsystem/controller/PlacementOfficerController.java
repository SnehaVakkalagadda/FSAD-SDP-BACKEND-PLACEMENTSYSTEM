package com.project.placementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.placementsystem.entity.Application;
import com.project.placementsystem.entity.Job;
import com.project.placementsystem.repository.JobRepository;
import com.project.placementsystem.service.PlacementOfficerService;

@RestController
@RequestMapping("officer")
public class PlacementOfficerController {
	@Autowired
    private PlacementOfficerService officerService;
    
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password)
//    {
//    	try {
//    		PlacementOfficer officer = officerService.verifyOfficerLogin(email, password);
//    		if(officer != null)
//    		{
//    			return ResponseEntity.status(200).body(officer);
//    		}
//    		else
//    		{
//    			return ResponseEntity.status(401).body("Login Invalid");
//    		}
//    	}
//    	catch (Exception e) {
//    		return ResponseEntity.status(500).body("Internal Server Error");
//		} 
//    }

    @GetMapping("/applications")
    public ResponseEntity<?> getAllApplications()
    {
    	try {
    		List<Application> list = officerService.getAllApplications();
    		if(list.isEmpty())
    		{
    			return ResponseEntity.status(404).body("Applications Not Found");
    		}
    		return ResponseEntity.status(200).body(list);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
    	}
    }

    @PutMapping("/updatestatus")
    public ResponseEntity<String> updateStatus(@RequestParam int applicationId, @RequestParam String status, @RequestParam int studentId)
    {
    	try {
    		String msg = officerService.updateApplicationStatus(applicationId, status, studentId);
    		return ResponseEntity.status(200).body(msg);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");		}
    }
    
    @Autowired
    private JobRepository JobRepository;
    
    @GetMapping("/viewjobs")
    public ResponseEntity<?> viewJobs() {
        try {
            List<Job> jobs = JobRepository.findAll();
            return ResponseEntity.status(200).body(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}