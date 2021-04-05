package com.smartcity.service;


import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.smartcity.model.EventVisiter;
import com.smartcity.model.User;
import com.smartcity.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	User saveNewUser(UserRegistrationDto registrationDto);
	
	User saveEmployer(UserRegistrationDto registrationDto);
	
	User saveOrganizer(UserRegistrationDto registrationDto);
	
	public Long getUserID(User user);

	List<User> listAll(String keyword);

	List<User> listEm(String keyword);

	List<User> listEm2();
	
	List<User> listOrg(String keyword);

	List<User> listOrg2();

	User updateEmployer(UserRegistrationDto registrationDto);

}