package com.smartcity.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.smartcity.model.Event;
import com.smartcity.repository.EventRepository;


public class OrganizerService {
	@Autowired
	 private EventRepository repo;
    
	    public List<Event> listAll(String keyword) {
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findAll();
	    }
	    
	    
		  public Optional < Event > findById(Long id) {
		        return repo.findById(id);
		    }
		  
}
