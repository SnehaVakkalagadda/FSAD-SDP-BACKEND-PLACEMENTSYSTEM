package com.klef.fsad.sdp.placementsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.placementsystem.dto.ApplicationViewDTO;
import com.klef.fsad.sdp.placementsystem.entity.Application;
import com.klef.fsad.sdp.placementsystem.entity.PlacementOfficer;
import com.klef.fsad.sdp.placementsystem.entity.Student;
import com.klef.fsad.sdp.placementsystem.entity.Job;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.repository.ApplicationRepository;
import com.klef.fsad.sdp.placementsystem.repository.EmployerRepository;
import com.klef.fsad.sdp.placementsystem.repository.JobRepository;
import com.klef.fsad.sdp.placementsystem.repository.PlacementOfficerRepository;
import com.klef.fsad.sdp.placementsystem.repository.StudentRepository;

@Service
public class PlacementOfficerServiceImpl implements PlacementOfficerService {
	@Autowired
    private PlacementOfficerRepository officerRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private JobRepository jobRepository;
    
    
    @Override
    public PlacementOfficer verifyOfficerLogin(String email, String pwd) 
    {
        return officerRepository.findByEmailAndPassword(email, pwd);
    }

//    @Override
//    public List<Application> getAllApplications() 
//    {
//        return applicationRepository.findAll();
//    }
    
    @Override
    public List<ApplicationViewDTO> getAllApplications()
    {
        List<Application> applications = applicationRepository.findAll();

        List<ApplicationViewDTO> dtoList = new ArrayList<>();

        for(Application app : applications)
        {
            Optional<Student> sOpt = studentRepository.findById(app.getStudentId());

            if(sOpt.isPresent())
            {
                Student s = sOpt.get();

                ApplicationViewDTO dto = new ApplicationViewDTO();

                dto.setApplicationId(app.getId());
                dto.setStudentName(s.getName());
                dto.setEmail(s.getEmail());
                dto.setBranch(s.getBranch());
                dto.setYear(s.getYear());
                dto.setStatus(app.getStatus());

                dto.setResumeUrl("http://localhost:2007/student/download-resume/" + s.getId());

                dtoList.add(dto);
            }
        }

        return dtoList;
    }

//    @Override
//    public String updateApplicationStatus(int applicationId, String status, int studentId) 
//    {
//        Optional<Application> optional = applicationRepository.findByStudentIdAndAppId(studentId,applicationId);
//        if(optional.isPresent())
//        {
//            Application app = optional.get();
//            app.setStatus(status.toUpperCase());
//            applicationRepository.save(app);
//            return "Application Status Updated Successfully";
//        }
//        else
//        {
//            return "Application ID Not Found";
//        }
//    }
//    
    @Override
    public String updateApplicationStatus(int applicationId, String status) 
    {
       Optional<Application> optional = applicationRepository.findById(applicationId);

      if(optional.isPresent())
    		{
         Application app = optional.get();
           app.setStatus(status.toUpperCase());
         applicationRepository.save(app);
            
    	
    	Optional<Student> sOpt = studentRepository.findById(app.getStudentId());

    	if(sOpt.isPresent())
    	{
    	    Student student = sOpt.get();

    	    String email = student.getEmail();

    	    //  Get job details
    	    Optional<Job> jOpt = jobRepository.findById(app.getJobId());

    	    String jobTitle = "";
    	    String companyName = "";

    	    if(jOpt.isPresent())
    	    {
    	        Job job = jOpt.get();
    	        jobTitle = job.getTitle();

    	        Optional<Employer> eOpt = employerRepository.findById(job.getEmployerId());
    	        if(eOpt.isPresent())
    	        {
    	            companyName = eOpt.get().getCompanyName();
    	        }
    	    }

    	    String subject = "Placement Application Update";

    	    String text = "";

    	    //  Dynamic message based on status
    	    if(status.equalsIgnoreCase("SELECTED"))
    	    {
    	        text = "Dear " + student.getName() + ",\n\n" +
    	               "🎉 Congratulations! You have been SELECTED for the role of " + jobTitle +
    	               " at " + companyName + ".\n\n" +
    	               "We wish you all the best!\n\nPlacement Cell";
    	    }
    	    else if(status.equalsIgnoreCase("REJECTED"))
    	    {
    	        text = "Dear " + student.getName() + ",\n\n" +
    	               "We regret to inform you that your application for " + jobTitle +
    	               " at " + companyName + " was not selected.\n\n" +
    	               "Keep trying. Best of luck!\n\nPlacement Cell";
    	    }
    	    else
    	    {
    	        text = "Dear " + student.getName() + ",\n\n" +
    	               "Your application status for " + jobTitle +
    	               " at " + companyName + " is now: " + status + ".\n\n" +
    	               "Regards,\nPlacement Cell";
    	    }

    	    emailService.sendEmail(email, subject, text);
    	}
    	return "Status Updated and Email Sent";
    	    }
    	    else
    	    {
    	        return "Application Not Found";
    	    }
    	}
}