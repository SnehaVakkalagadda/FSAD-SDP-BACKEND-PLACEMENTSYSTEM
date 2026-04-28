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
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.entity.Student;
import com.klef.fsad.sdp.placementsystem.service.StudentService;

@RestController
@RequestMapping("student")
public class StudentController 
{
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/")
    public String home() 
    {
    	return "Student Dashboard";
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody Student student)
//    {
//    	try {
//    		String msg= studentService.studentRegistration(student);
//    		return ResponseEntity.status(200).body(msg);
//    	}
//    	catch(Exception e) {
//    		return ResponseEntity.status(500).body("Internal Server Error");
//    	}
//    }
    
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String branch,
            @RequestParam float cgpa,
            @RequestParam int year,
            @RequestParam String username,
            @RequestParam String collegeName,
            @RequestParam String contact,
            @RequestParam MultipartFile file)   // 🔥 resume
    {
        try 
        {
            String msg = studentService.studentRegistration(
                    name, email, password, branch, cgpa, year,
                    username, collegeName, contact, file);

            return ResponseEntity.status(200).body(msg);
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password)
//    {
//    	try {
//    		Student s = studentService.verifyStudentLogin(email, password);
//    		if(s != null)
//    		{
//    			return ResponseEntity.status(200).body(s);
//    		}
//    		else
//    		{
//    			return ResponseEntity.status(401).body("Login Invalid");
//    		}
//    	}
//    	catch(Exception e) {
//    		return ResponseEntity.status(500).body("Internal Server Error");
//    	}
//    }
    

//    @PutMapping("/updateprofile")
//    public ResponseEntity<String> update(@RequestBody Student student)
//    {
//    	try {
//        	String msg = studentService.updateStudentProfile(student);
//            return ResponseEntity.status(201).body(msg);
//    	}
//        catch(Exception e) {
//        	return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
    
    @PutMapping(value = "/update", consumes = "multipart/form-data")
    public ResponseEntity<String> update(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String branch,
            @RequestParam float cgpa,
            @RequestParam int year,
            @RequestParam String username,
            @RequestParam String collegeName,
            @RequestParam String contact,
            @RequestParam(required = false) MultipartFile file)
    {
        try 
        {
            String msg = studentService.updateStudentProfile(
                    id, name, email, branch, cgpa, year,
                    username, collegeName, contact, file);

            return ResponseEntity.status(200).body(msg);
        }
        catch(Exception e) 
        {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/viewjobs")
    public ResponseEntity<?> viewJobs()
    {
    	System.out.println("CONTROLLER HIT");
    	try {
    		List<Job> jobs = studentService.viewJobs();
    		return ResponseEntity.status(200).body(jobs);
    	}
    	catch(Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
    	}
    }

    @PostMapping("/applyjob")
    public ResponseEntity<String> apply(@RequestBody ApplicationDTO dto)
    {
    	try
    	{
    		String msg = studentService.applyJob(dto);
    		return ResponseEntity.status(200).body(msg);
    	}
    	catch(Exception e){
    		return ResponseEntity.status(500).body("Internal Server Error");
    	}
    }

    @GetMapping("/viewapplications/{id}")
    public ResponseEntity<?> track(@PathVariable int id)
    {
    	try {
    		List<Application> list = studentService.trackApplications(id);
    		if(list.isEmpty())
    		{
    			return ResponseEntity.status(404).body("Application not found");
    		}
    		return ResponseEntity.status(200).body(list);
    	}
    	catch(Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
    	}
    }
}