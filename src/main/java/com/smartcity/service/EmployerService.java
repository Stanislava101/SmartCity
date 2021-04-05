package com.smartcity.service;

import java.util.Arrays;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartcity.model.Employer;
import com.smartcity.repository.EmployerRepository;

@Service
public class EmployerService {
	@Autowired
	 private EmployerRepository repo;
	
	    public List<Employer> listAll(String keyword) {
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findAll();
	    }
			
			
			  public Optional <Employer > findById(Long id) {
			        return repo.findById(id);
			    }
			  
}
