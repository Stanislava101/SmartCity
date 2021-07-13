package com.smartcity.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcity.model.User;
import com.smartcity.model.UserData;
import com.smartcity.repository.UserDataRepository;

@Service
public class UserDataService {
	
	@Autowired
	private UserDataRepository userDataRepository;
	@Transactional
	public void addMoreDetails(User user) {
			userDataRepository.save(new UserData(user," "));
	}
	
	
}