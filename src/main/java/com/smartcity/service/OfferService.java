package com.smartcity.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartcity.repository.OfferRepository;
import com.smartcity.repository.UserApplicationRepository;
import com.smartcity.model.Event;
import com.smartcity.model.EventVisiter;
import com.smartcity.model.Offer;
import com.smartcity.model.Role;
import com.smartcity.model.User;
import com.smartcity.model.UserApplication;
@Service
public class OfferService {
	@Autowired
	 private OfferRepository repo;
	
	@Autowired
	private UserApplicationRepository repository;
     
	    public List<Offer> listAll(String keyword) {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String email = ((UserDetails)principal).getUsername();
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findAllByEmp(email);
	    }
	    
	    public List<Offer> listAllOffers(String keyword) {
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findAll();
	    }
	    
	    public List<Offer> listAllUsers(String keyword) {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String email = ((UserDetails)principal).getUsername();
	        if (keyword != null) {
	            return repo.findAll(keyword);
	        }
	        return repo.findAll();
	    }
	    
		  public Optional < Offer > findById(Long id) {
		        return repo.findById(id);
		    }
		
		  
			public List<UserApplication> listAllAppliedOffers(String keyword) {
			    if (keyword != null) {
			        return repository.findAll(keyword);
			    }
			    return repository.findAll();
			}
		    
			public List<UserApplication> listAllByID(String id) {
			    if (id != null) {
			        return repository.findByID(id);
			    }
			    return repository.findAll();
			}
			
		    
		    public void addOffer(UserApplication entity,long id, User clientID) {
		    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    	String username="";
		    	if (principal instanceof UserDetails) {
		    	  username = ((UserDetails)principal).getUsername();
		    	} else {
		    	   username = principal.toString();
		    	}
		    		entity.setOfferID(id);
					entity.setName(username);
					//entity.setCandidateID(candidateID);
					entity.setClient(clientID);
					entity = repository.save(entity);
				}
		    
		    public void saveOffer(Offer entity, String username) {
					entity.setEmployer(username);
					System.out.println(username);
					entity = repo.save(entity);
				}
		    
		     public long count(long id) {  
		    	 if (id != 0) {
		    		 return repository.count(id);
				    }
		    	  return repository.count(id);
		     }
		     
		/*    
			@Transactional
			public void offer(long offerID, long candidateID) {
				UserApplication cc = new UserApplication("ivan",candidateID,offerID);
				Offer oneSale = new Offer(cc);
				Set<Offer> itemsSet = new HashSet<Offer>();
					itemsSet.add(oneSale);	
					repo.save(new Offer(cc));
			}*/
}
