package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationViewDTO;
import com.klef.fsad.sdp.placementsystem.entity.PlacementOfficer;

public interface PlacementOfficerService {

    public PlacementOfficer verifyOfficerLogin(String email, String pwd);

    public List<ApplicationViewDTO> getAllApplications();

    public String updateApplicationStatus(int applicationId, String status);
}