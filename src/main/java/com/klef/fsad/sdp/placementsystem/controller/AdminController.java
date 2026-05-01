package com.klef.fsad.sdp.placementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.placementsystem.entity.Admin;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.PlacementOfficer;
import com.klef.fsad.sdp.placementsystem.entity.Student;
import com.klef.fsad.sdp.placementsystem.service.AdminService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*")
public class AdminController {
	@Autowired
    private AdminService adminService;
	
	@GetMapping("/")
	public String adminHome() {
		return "Admin Dashboard";
	}
	
//    @PostMapping("/login")
//    public ResponseEntity<?> verifyLogin(@RequestParam String email, @RequestParam String password)
//    {
//    	try {
//    		Admin admin = adminService.verifyAdminLogin(email, password);
//    		if(admin != null)
//    		{
//    			return ResponseEntity.status(200).body(admin);    		
//    		}
//    		else
//    		{
//    			return ResponseEntity.status(404).body("Login Invalid");
//    		}
//    	}
//    	catch (Exception e) {
//    		return ResponseEntity.status(500).body("Internal Server Error");
//		}
//    }
    
    @GetMapping("/displaystudents")
    public ResponseEntity<?> viewAllStudents(){
    	try {
			List<Student> students=adminService.viewAllStudents();
			if(students.isEmpty())
				return ResponseEntity.status(404).body("No Student Data Found");
			else
				return ResponseEntity.status(200).body(students);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
    
    @GetMapping("/displayemployers")
    public ResponseEntity<?> viewAllEmployers(){
    	try {
			List<Employer> employers=adminService.viewAllEmployers();
			if(employers.isEmpty())
				return ResponseEntity.status(404).body("No Employer Data Found");
			else
				return ResponseEntity.status(200).body(employers);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
    
    @GetMapping("/displayofficers")
    public ResponseEntity<?> viewAllOfficers(){
    	try {
			List<PlacementOfficer> officers=adminService.viewAllOfficers();
			if(officers.isEmpty())
				return ResponseEntity.status(404).body("No Officer Data Found");
			else
				return ResponseEntity.status(200).body(officers);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
    
    @DeleteMapping("/deletestudent")
    public ResponseEntity<String> deleteStudent(@RequestParam int id){
    	try {
    		String msg = adminService.deleteStudent(id);
    		if(msg.contains("Successfully"))
    			return ResponseEntity.status(200).body(msg);	
    		else
    			return ResponseEntity.status(404).body(msg);	
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
    
    @DeleteMapping("/deletemploeyr")
    public ResponseEntity<String> deleteEmployer(@RequestParam int id){
    	try {
    		String msg = adminService.deleteEmployer(id);
    		if(msg.contains("Successfully"))
    			return ResponseEntity.status(200).body(msg);	
    		else
    			return ResponseEntity.status(404).body(msg);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
    
    @DeleteMapping("/deleteofficer/{id}")
    public ResponseEntity<String> deleteOfficer(@PathVariable int id){
    	try {
    		String msg = adminService.deleteOfficer(id);
    		if(msg.contains("Successfully"))
    			return ResponseEntity.status(200).body(msg);	
    		else
    			return ResponseEntity.status(404).body(msg);
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
    
//    @PostMapping("/addstudent")
//    public ResponseEntity<String> addStudent(@RequestBody Student student)
//    {
//    	try {
//    		String msg = adminService.addStudent(student);
//    		return ResponseEntity.status(201).body(msg);	
//    	}
//    	catch (Exception e) {
//    		return ResponseEntity.status(500).body("Internal Server Error");
//		}
//    }
//
//    @PostMapping("/addemployer")
//    public ResponseEntity<String> addEmployer(@RequestBody Employer employer)
//    {
//        try {
//        	String msg = adminService.addEmployer(employer);
//    		return ResponseEntity.status(201).body(msg);	
//    	}
//    	catch (Exception e) {
//    		return ResponseEntity.status(500).body("Internal Server Error");
//		}
//    }
    
    @PostMapping("/addofficer")
    public ResponseEntity<String> addOfficer(@RequestBody PlacementOfficer officer)
    {
        try {
        	String msg = adminService.addPlacementOfficer(officer);
    		return ResponseEntity.status(201).body(msg);	
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Internal Server Error");
		}
    }
}
