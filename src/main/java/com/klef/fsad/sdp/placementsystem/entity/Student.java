package com.klef.fsad.sdp.placementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    @Column(length=50,nullable=false)
	    private String name;
	    @Column(length=50,nullable=false,unique=true)
	    private String email;
	    @Column(nullable=false)
	    private String password;
	    @Column(length=50,nullable=false)
	    private String branch;
	    @Column(nullable=false)
	    private String resume;
	    @Column(nullable=false)
	    private float cgpa;
	    @Column(nullable=false)
	    private int year;
	    @Column(nullable = false, unique = true, length = 100)
	    private String username;
	    @Column(length = 50, nullable = false)
	    private String collegeName;
	    @Column(length = 50, nullable = false, unique=true)
	    private String contact;
	    
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getBranch() {
			return branch;
		}
		public void setBranch(String branch) {
			this.branch = branch;
		}
		public String getResume() {
			return resume;
		}
		public void setResume(String resume) {
			this.resume = resume;
		}
		public float getCgpa() {
			return cgpa;
		}
		public void setCgpa(float cgpa) {
			this.cgpa = cgpa;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public String getCollegeName() {
			return collegeName;
		}
		public void setCollegeName(String collegeName) {
			this.collegeName = collegeName;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		
}
