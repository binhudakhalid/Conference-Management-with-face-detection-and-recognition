package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "people")
public class People {
	
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", lastName=" + lastName + ", educationLevel=" + educationLevel
				+ ", contactNumber=" + contactNumber + ", nic=" + nic;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name")
	private String name;
	
	
	@Column(name = "last_name")
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	
	
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	
	@Column(name = "education")
	@NotEmpty(message = "*Please provide your education Level")
	private String educationLevel;
	
	
	@Column(name = "contact_number") 
	@NotEmpty(message = "*Please provide your Contact number")
    private String contactNumber;
	
	
	@Column(name = "nic",unique = true)
	@NotEmpty(message = "*Please provide your nic")
	private String nic;
    
	
	
	
    
    public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEducationLevel() {
		return educationLevel;
	}


	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}


	public String getNic() {
		return nic;
	}


	public void setNic(String nic) {
		this.nic = nic;
	}


	public People() {}


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


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public People(int id, String name, String lastName, String contactNumber) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
	}
    
    
    
}
