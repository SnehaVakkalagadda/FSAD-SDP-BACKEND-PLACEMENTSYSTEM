package com.project.placementsystem.service;

import java.util.List;

import com.project.placementsystem.entity.Application;
import com.project.placementsystem.entity.PlacementOfficer;

public interface PlacementOfficerService {
	public PlacementOfficer verifyOfficerLogin(String email, String pwd);
    public List<Application> getAllApplications();
    public String updateApplicationStatus(int applicationId, String status, int studentId);
}