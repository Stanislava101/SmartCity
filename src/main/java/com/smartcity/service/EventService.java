package com.smartcity.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartcity.model.Event;
import com.smartcity.model.EventVisiter;
import com.smartcity.model.User;
import com.smartcity.repository.EventRepository;
import com.smartcity.repository.EventVisiterRepository;

@Service
public class EventService {
	@Autowired
	 private EventRepository repo;
	
	@Autowired
	 private EventVisiterRepository repository;
    
	    public List<Event> listAll(String keyword) {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String email = ((UserDetails)principal).getUsername();
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findEventByOrg(email);
	    }
	    
	    
	    
	    public List<Event> listAllEvents(String keyword) {
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findAll();
	    }
	    
	    public void attendEvent(EventVisiter entity,long id, User user) {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	String username="";
	    	if (principal instanceof UserDetails) {
	    	  username = ((UserDetails)principal).getUsername();
	    	} else {
	    	   username = principal.toString();
	    	}
	    		entity.setEventID(id);
				entity.setName(username);
				entity.setParticipant(user);
				entity = repository.save(entity);
			}

	    
	    public void saveEventData(Event entity, String username) {
				entity.setOrganizer(username);
				System.out.println(username);
				entity = repo.save(entity);
			}
	    
	public List<EventVisiter> listAllVisiters(String keyword) {
    if (keyword != null) {
        return repository.findAll(keyword);
    }
    return repository.findAll();
}
	
	public List<Event> listEvent(long id) {
	    if (id != 0) {
	        return repo.findEventById(id);
	    }
	    return repo.findAll();
	}   
	public Optional<Event> getEventById(long id) {
		return repo.findById(id);
	}
	
	  public Optional < Event > findById(Long id) {
	        return repo.findById(id);
	    }
		public List<EventVisiter> listAllByID(String id) {
		    if (id != null) {
		        return repository.findByID(id);
		    }
		    return repository.findAll();
		}
		
	     public int count(long id) {  
	    	 if (id != 0) {
	    		 return repository.count(id);
			    }
	    	  return repository.count(id);
	     }

	     public Boolean check(long eventID,String name) {
	    	 long result = repository.checkVisiter(eventID,name);
	    	 boolean participantExist = false;
	    	 if(result==1) {
	    		 participantExist = true;
	    	 }
	    	 return participantExist;
	     }
	    
}