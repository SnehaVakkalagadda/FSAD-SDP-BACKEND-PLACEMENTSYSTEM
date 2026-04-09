package com.project.placementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.placementsystem.entity.Admin;
import com.project.placementsystem.entity.Employer;
import com.project.placementsystem.entity.PlacementOfficer;
import com.project.placementsystem.entity.Student;
import com.project.placementsystem.repository.AdminRepository;
import com.project.placementsystem.repository.EmployerRepository;
import com.project.placementsystem.repository.PlacementOfficerRepository;
import com.project.placementsystem.repository.StudentRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private PlacementOfficerRepository officerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin verifyAdminLogin(String username, String pwd) 
    {
        return adminRepository.findByUsernameAndPassword(username, pwd);
    }

    @Override
    public String addPlacementOfficer(PlacementOfficer officer) 
    {
    	// IMPORTANT: Encrypt password before saving
        if (officer.getPassword() != null && !officer.getPassword().isEmpty()) 
        {
            String encodedPassword = passwordEncoder.encode(officer.getPassword());
            officer.setPassword(encodedPassword);
        }

        officerRepository.save(officer);
        return "Placement Officer Added Successfully";
    }

	@Override
	public List<Student> viewAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public String deleteStudent(int id) {
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			return "Student Record Deleted Successfully";
		}
		return "Student Data Not Found";
	}

	@Override
	public List<Employer> viewAllEmployers() {
		return employerRepository.findAll();
	}

	@Override
	public String deleteEmployer(int id) {
		if(employerRepository.existsById(id)) {
			employerRepository.deleteById(id);
			return "Employer Record Deleted Successfully";
		}
		return "Employer Data Not Found";
	}

	@Override
	public List<PlacementOfficer> viewAllOfficers() {
		return officerRepository.findAll();
	}

	@Override
	public String deleteOfficer(int id) {
		if(officerRepository.existsById(id)) {
			officerRepository.deleteById(id);
			return "Officer Record Deleted Successfully";
		}
		return "Officer Data Not Found";
	}
}