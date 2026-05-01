package com.klef.fsad.sdp.placementsystem.service;

import java.util.List;

import com.klef.fsad.sdp.placementsystem.entity.Admin;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.PlacementOfficer;
import com.klef.fsad.sdp.placementsystem.entity.Student;

public interface AdminService {
	public Admin verifyAdminLogin(String username, String pwd);
	
    //public String addStudent(Student student);
	public List<Student> viewAllStudents();
	public String deleteStudent(int id);
	
    //public String addEmployer(Employer employer);
	public List<Employer> viewAllEmployers();
	public String deleteEmployer(int id);
	
	public List<PlacementOfficer> viewAllOfficers();
	public String deleteOfficer(int id);
    public String addPlacementOfficer(PlacementOfficer officer);
}