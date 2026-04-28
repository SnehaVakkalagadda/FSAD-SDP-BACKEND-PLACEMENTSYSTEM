package com.klef.fsad.sdp.placementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationViewDTO;
import com.klef.fsad.sdp.placementsystem.dto.JobDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.service.EmployerService;

@RestController
@RequestMapping("employer")

public class EmployerController {
	@Autowired
    private EmployerService employerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employer employer)
    {
    	try {
    		String msg = employerService.employerRegistration(employer);
    		return ResponseEntity.status(201).body(msg);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
		}
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password)
//    {
//    	try {
//    		Employer e = employerService.verifyEmployerLogin(email, password);
//    		if(e != null)
//    		{
//    			return ResponseEntity.status(200).body(e);
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

    @PutMapping("/updateprofile")
    public ResponseEntity<String> update(@RequestBody Employer employer)
    {
    	try {
    		String msg = employerService.updateEmployerProfile(employer);
    		return ResponseEntity.status(200).body(msg);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
    	}
    }

    @PostMapping("/postjob")
    public ResponseEntity<String> postJob(@RequestBody JobDTO dto)
    {
    	try {
    		String msg = employerService.postJob(dto);
    		return ResponseEntity.status(200).body(msg);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
    	}
    }

    @GetMapping("/viewjobs/{id}")
    public ResponseEntity<?> viewJobs(@PathVariable int id)
    {
    	try {
    		List<Job> jobs = employerService.viewJobsByEmployer(id);
    		if(jobs.isEmpty())
    		{
    			return ResponseEntity.status(404).body("No Jobs Found");
    		}
    		return ResponseEntity.status(200).body(jobs);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(200).body("Internal Server Error");
    	}
    }

//    @GetMapping("/applicationsperjob/{jobId}")
//    public ResponseEntity<?> viewApps(@PathVariable int jobId)
//    {
//    	try {
//    		List<Application> list = employerService.viewApplicationsByJob(jobId);
//    		if(list.isEmpty())
//    		{
//    			return ResponseEntity.status(404).body("No Applications Found");
//    		}
//    		return ResponseEntity.status(200).body(list);
//    	}
//    	catch (Exception e) {
//			return ResponseEntity.status(500).body("Internal Server Error");
//		}
//    }
    
    @GetMapping("/viewapplications/{jobId}")
    public ResponseEntity<?> viewApplications(@PathVariable int jobId)
    {
        List<ApplicationViewDTO> list = employerService.viewApplicationsByJob(jobId);

        return ResponseEntity.ok(list);
    }
}