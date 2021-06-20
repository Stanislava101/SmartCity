package com.smartcity.service;

import java.text.DecimalFormat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartcity.model.Offer;
import com.smartcity.model.User;
import com.smartcity.model.UserApplication;
import com.smartcity.model.UserData;
import com.smartcity.repository.UserDataRepository;

@Service
public class UserDataService {
	
	@Autowired
	private UserDataRepository userDataRepository;
	private long ID;
	@Transactional
	public void addMoreDetails(User user) {
			userDataRepository.save(new UserData(user," "));
	}
	
	
//	  public UserData findID(long id) {
	//	    UserData person = userDataRepository.findByUser(id);
	//		return person;
//		  }
	
}