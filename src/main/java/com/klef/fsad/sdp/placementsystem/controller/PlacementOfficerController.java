package com.klef.fsad.sdp.placementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationViewDTO;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.repository.JobRepository;
import com.klef.fsad.sdp.placementsystem.service.PlacementOfficerService;

@RestController
@RequestMapping("officer")
public class PlacementOfficerController {

    @Autowired
    private PlacementOfficerService officerService;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/viewapplications")
    public ResponseEntity<?> getAll() {
        List<ApplicationViewDTO> list = officerService.getAllApplications();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/updatestatus")
    public ResponseEntity<String> updateStatus(
            @RequestParam int applicationId,
            @RequestParam String status) {
        try {
            String msg = officerService.updateApplicationStatus(applicationId, status);
            return ResponseEntity.status(200).body(msg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/viewjobs")
    public ResponseEntity<?> viewJobs() {
        try {
            List<Job> jobs = jobRepository.findAll();
            return ResponseEntity.status(200).body(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}