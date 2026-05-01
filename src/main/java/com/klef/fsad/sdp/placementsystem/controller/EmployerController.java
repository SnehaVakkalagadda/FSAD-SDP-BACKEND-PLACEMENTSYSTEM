package com.klef.fsad.sdp.placementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationViewDTO;
import com.klef.fsad.sdp.placementsystem.dto.JobDTO;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.service.EmployerService;

@RestController
@RequestMapping("employer")
@CrossOrigin(origins = "*")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employer employer) {
        try {
            String msg = employerService.employerRegistration(employer);
            return ResponseEntity.status(201).body(msg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<String> update(@RequestBody Employer employer) {
        try {
            String msg = employerService.updateEmployerProfile(employer);
            return ResponseEntity.status(200).body(msg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/postjob")
    public ResponseEntity<String> postJob(@RequestBody JobDTO dto) {
        try {
            String msg = employerService.postJob(dto);
            return ResponseEntity.status(200).body(msg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/viewjobs/{id}")
    public ResponseEntity<?> viewJobs(@PathVariable int id) {
        try {
            List<Job> jobs = employerService.viewJobsByEmployer(id);

            if (jobs.isEmpty()) {
                return ResponseEntity.status(404).body("No Jobs Found");
            }

            return ResponseEntity.status(200).body(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/viewapplications/{jobId}")
    public ResponseEntity<?> viewApplications(@PathVariable int jobId) {
        List<ApplicationViewDTO> list = employerService.viewApplicationsByJob(jobId);
        return ResponseEntity.ok(list);
    }
}