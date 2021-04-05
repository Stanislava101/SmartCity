package com.smartcity.web;

import java.util.List;

import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smartcity.model.Event;
import com.smartcity.model.EventVisiter;
import com.smartcity.model.Offer;
import com.smartcity.model.User;
import com.smartcity.model.UserApplication;
import com.smartcity.repository.EventRepository;
import com.smartcity.repository.EventVisiterRepository;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.EventService;

@Controller
@RequestMapping("/events/")
public class EventController {

	@Autowired
	private EventRepository eventRepository;	
	
	@Autowired
	private EventVisiterRepository repo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventService service;
	
	private long visiterID;
	
	@GetMapping("showForm")
	public String showStudentForm(Event event) {
		return "add-event";
	}
	
	@RequestMapping("list")
	public String events(Model model, @Param("keyword") String keyword) {
		    List<Event> listOffers = service.listAll(keyword);
	        model.addAttribute("events", listOffers);
	        model.addAttribute("keyword", keyword);
		return "list-events";
	}
		
	 @RequestMapping("/my-profile")
	    public String viewEvents(Model model) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
	        List<EventVisiter> listEvents = service.listAllVisiters(username);
	        model.addAttribute("events", listEvents);
	        return "profile";
	    }
	@GetMapping("view-events")
	public String userEventsList(Model model) {
		model.addAttribute("events", this.eventRepository.findAll());
		return "view-events";
	}
	
	@PostMapping("add")
	public String addStudent(@Valid Event events, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-event";
		
		}
		
		this.eventRepository.save(events);
		return "redirect:list";
	}
	
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
		Event event = this.eventRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
		
		model.addAttribute("event", event);
		return "update-event";
	}
	
	@PostMapping("update/{id}")
	public String updateStudent(@PathVariable("id") long id, @Valid Event event, BindingResult result, Model model) {
		if(result.hasErrors()) {
			event.setId(id);
			return "update-event";
		}
		
		
		// update events
		eventRepository.save(event);
		
		// get all events ( with update)
		model.addAttribute("events", this.eventRepository.findAll());
		return "list-events";
	}
	
	@GetMapping("delete/{id}")
	public String deleteStudent(@PathVariable ("id") long id, Model model) {
		
		Event event = this.eventRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
		
		this.eventRepository.delete(event);
		model.addAttribute("events", this.eventRepository.findAll());
		return "list-events";
		
	}
	 @RequestMapping("/filterEvents")
	    public String viewFilteredEvents(Model model, @Param("keyword") String keyword) {
	        List<Event> listOffers = service.listAllEvents(keyword);
	        model.addAttribute("events", listOffers);
	        model.addAttribute("keyword", keyword);
	        return "filter-events";
	    }
	 
		@GetMapping("AttendEvent/{id}")
		public String visiter(@PathVariable ("id") long id,Model model) {
			Event event = this.eventRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
	
			model.addAttribute("events", this.eventRepository.findAll());
			visiterID = id;
			model.addAttribute("event", event);
			return "added-event";
		}
		
		@GetMapping("view-event/{id}")
		public String showEvent(@PathVariable ("id") long id, Model model) {
			Event event = this.eventRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
			
			model.addAttribute("event", event);
			return "page";
		}
		
		@GetMapping("interested-event/{id}")
		public String interestedEventDetails(@PathVariable ("id") long id, Model model) {
			EventVisiter event = this.repo.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
			long eventID = event.getEventID();
			Event theEvent = this.eventRepository.findById(eventID)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));

			model.addAttribute("theEvent", theEvent);
			model.addAttribute("event", event);
			return "attending-details";
		}
		
		@GetMapping("event-details/{id}")
		public String showEventAdmin(@PathVariable ("id") long id, Model model) {
			Event event = this.eventRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
			
			model.addAttribute("event", event);
			return "admin-eventview";
		}
		

		
		@PostMapping("added")
		public String addVisiter(@Valid EventVisiter events, BindingResult result, Model model,User user) {
			if(result.hasErrors()) {
				return "added-event";
			
			}
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 String email = ((UserDetails)principal).getUsername();
			Event event = this.eventRepository.findById(visiterID)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + visiterID));
			String organizer = event.getOrganizer();
			user = this.userRepository.findByEmail(email);
			
			service.attendEvent(events,visiterID,user);
		
			return "redirect:filterEvents";
		}
}
