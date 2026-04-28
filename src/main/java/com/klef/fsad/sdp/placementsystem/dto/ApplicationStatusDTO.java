package com.klef.fsad.sdp.placementsystem.dto;

public class ApplicationStatusDTO {
	private int applicationId;
    private String status;
    
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}