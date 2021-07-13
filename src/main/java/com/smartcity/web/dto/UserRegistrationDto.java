package com.smartcity.web.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDto {
	private long id;
	private String firstName;
	private String lastName;
  //  @Size(min = 10, max = 12, message = "Valid number of digits is between 10 and 12")
	@Pattern(regexp="(^$|[0-9]{10,12})", message="Enter digits only length between 10 and 12")
	@Column(name = "phone_no")
	private String phoneNo;
	@Email(message = "Email is not valid")
	private String email;
  //  @Size(min = 9, max = 15, message = "Your password must be between 9 and 15 characters")
    @Pattern(regexp="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{9,}$", message="Enter valid password with minimum nine characters, at least one letter and one number")
	private String password;
	
	public UserRegistrationDto(){
		
	}
	
	public UserRegistrationDto(String firstName, String lastName, String phoneNo, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setId(long id) {
		this.id=id;
		
	}

}
