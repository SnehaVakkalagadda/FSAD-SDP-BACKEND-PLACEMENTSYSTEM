package com.project.placementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserServiceImpl implements UserService 
{
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private StudentRepository studentRepo;
    
    @Autowired
    private PlacementOfficerRepository officerRepo;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException 
    {
        // 1. Try Admin - using username
        Optional<Admin> adminOpt = adminRepo.findById(input);   // Admin uses username as ID
        if (adminOpt.isPresent()) 
        {
            Admin admin = adminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(), 
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ADMIN"))
            );
        }

        // 2. Try Customer - using email
        Optional<Student> studentOpt = studentRepo.getStudentByEmailOrUsername(input, input);
        if (studentOpt.isPresent()) 
        {
            Student student = studentOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    student.getEmail(), 
                    student.getPassword(),
                    List.of(new SimpleGrantedAuthority("STUDENT"))
            );
        }

        // 3. Try Employer - using employerEmail
        Optional<Employer> employerOpt = employerRepo.findByEmail(input);
        if (employerOpt.isPresent()) 
        {
            Employer employer = employerOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    employer.getEmail(),
                    employer.getPassword(),
                    List.of(new SimpleGrantedAuthority("EMPLOYER"))
            );
        }
     // 3. Try Placement Officer - using email and password
        Optional<PlacementOfficer> officerOpt = officerRepo.getOfficerByEmailOrUsername(input, input);
        if (officerOpt.isPresent()) 
        {
            PlacementOfficer officer = officerOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    officer.getEmail(),
                    officer.getPassword(),
                    List.of(new SimpleGrantedAuthority("PLACEMENT OFFICER"))
            );
        }

        throw new UsernameNotFoundException("User not found with input: " + input);
    }

	@Override
	public Object getUserByLogin(String input) 
	{
		 Optional<Admin> adminOpt = adminRepo.findById(input);
		 
	        if (adminOpt.isPresent()) 
	        {
	            return adminOpt.get();
	        }

	        Optional<Student> studentOpt = studentRepo.findByEmail(input);
	        if (studentOpt.isPresent()) 
	        {
	            return studentOpt.get();
	        }

	        Optional<PlacementOfficer> officerOpt = officerRepo.findByEmail(input);
	        if (officerOpt.isPresent()) 
	        {
	            return officerOpt.get();
	        }
	        
	        Optional<Employer> employerOpt = employerRepo.findByEmail(input);
	        if (employerOpt.isPresent()) 
	        {
	            return employerOpt.get();
	        }

	        return null;
	}
}