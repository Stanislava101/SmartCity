package com.smartcity.web;


import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcity.model.Employer;
import com.smartcity.model.Event;
import com.smartcity.model.EventVisiter;
import com.smartcity.model.Offer;
import com.smartcity.model.Organizer;
import com.smartcity.model.User;
import com.smartcity.model.UserApplication;
import com.smartcity.repository.EventRepository;
import com.smartcity.repository.EventVisiterRepository;
import com.smartcity.repository.OrganizerRepository;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.EventService;
import com.smartcity.service.MailService;
import com.smartcity.service.OrganizerService;
import com.smartcity.service.UserService;
import com.smartcity.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/organizers/")
public class OrganizerController {
	
	@Autowired
	private EventVisiterRepository repository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;
	

	@GetMapping("list")
	public String organizers(Model model,@Param("keyword") String keyword) {
		List<User> listOrganizers = userService.listOrg(keyword);
		model.addAttribute("organizers", listOrganizers);
		model.addAttribute("keyword", keyword);
		return "list-organizers";
	}
	 
	 @GetMapping("listOrganizerEvent")
	    public String viewFilteredEvents(Model model, @Param("keyword") String keyword) {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String email = ((UserDetails)principal).getUsername();
	        List<Event> listOfEvents = eventService.listAll(keyword);
	        model.addAttribute("events", listOfEvents);
	        model.addAttribute("keyword", keyword);
	        return "list-organizers-events";
	    
	 }
		@GetMapping("deleteOrg/{id}")
		public String deleteOrganizer(@PathVariable ("id") long id, Model model) {
			
			User organizer = this.userRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid organizer id : " + id));
			
			this.userRepository.delete(organizer);
			model.addAttribute("organizers", userService.listOrg2());
			return "list-organizers";
			
		}
	 
		@GetMapping("view/{id}")
		public String viewDetails(@PathVariable ("id") long id, Model model) {
			User organizer = this.userRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid organizer id : " + id));
			
			model.addAttribute("event", organizer);
			return "view-organizer";
		}
	@GetMapping("editEvent/{id}")
	public String organizerUpdateEvent(@PathVariable ("id") long id, Model model) {
		Event event = this.eventRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
		
		model.addAttribute("event", event);
		return "update-organizer-event";
	}
	
	
	@PostMapping("updateEvent/{id}")
	public String updateOrganizerEvent(@PathVariable("id") long id, @Valid Event event, BindingResult result, Model model) {
		if(result.hasErrors()) {
			event.setId(id);
			return "update-organizer-event";
		}
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
		 System.out.println(username);
		 event.setOrganizer(username);
		// update events
		eventRepository.save(event);
		
		// get all events ( with update)
		model.addAttribute("events",this.eventRepository.findEventByOrg(username));
		return "list-organizers-events";
	}
		
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
		User organizer = this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid organizer id : " + id));
		
		model.addAttribute("organizer", organizer);
		return "update-organizer";
	}
	
	@PostMapping("update/{id}")
	public String updateOrganizer(@PathVariable("id") long id,Organizer organizer, UserRegistrationDto registrationDto, Model model) {
		User updateOrganizer = userRepository.getOne(id);
		updateOrganizer.setFirstName(registrationDto.getFirstName());
		updateOrganizer.setLastName(registrationDto.getLastName());
		updateOrganizer.setEmail(registrationDto.getEmail());
		updateOrganizer.setPhoneNo(registrationDto.getPhoneNo());
		userRepository.save(updateOrganizer);
		
		// get all organizers ( with update)
		model.addAttribute("organizers", this.userRepository.findOrg2());
		return "list-organizers";
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping("add-organizer")
	public String registerOrganizer() {
		return "add-organizer";
	}
	
	@PostMapping("add-organizer")
	public String addOrganizer(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			return "add-organizer";
		}
		userService.saveOrganizer(registrationDto);
		return "redirect:/organizers/add-organizer?success";
	}
	
	@GetMapping("view-event/{id}")
	public String showEvent(@PathVariable ("id") long id, Model model) {
		Event event = this.eventRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
		
		model.addAttribute("event", event);
		return "view-event-organizer";
	}
	
	@GetMapping("delete/{id}")
	public String deleteEvent(@PathVariable ("id") long id, Model model) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails)principal).getUsername();
		Event event = this.eventRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
		
		this.eventRepository.delete(event);
		model.addAttribute("events", this.eventRepository.findEventByOrg(email));
		return "list-organizers-events";
		
	}
	@GetMapping("showForm")
	public String addEvent(Event event) {
		return "add-event-organizer";
	}
	
	@PostMapping("add")
	public String addEventData(@Valid Event events, BindingResult result, Model model) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
		 
		if(result.hasErrors()) {
			return "add-event-organizer";
		
		}
		System.out.println(username);
		 events.setOrganizer(username);
		this.eventRepository.save(events);
		return "redirect:listOrganizerEvent";
	}
	 @RequestMapping("/participants/{id}")
	    public String candidates(Model model, @PathVariable ("id") Long id) throws AddressException, MessagingException {
		String filterID = String.valueOf(id);
		MailService sendEmail = new MailService();
		 List<EventVisiter> listEvents = eventService.listAllByID(filterID);
	       model.addAttribute("events", listEvents);
	        Event event = this.eventRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid event id : " + id));
	        Integer ev1 = eventService.count(id);
	        Integer nums = eventRepository.eventNum(id);
	        String numParticipants = "";
	        System.out.println(nums);
	        if(ev1 == nums) {
	 			sendEmail.getMailProperties();
	        	numParticipants = "Maximum capacity has been reached";
	        }

	        model.addAttribute("numParticipants",numParticipants);
	        model.addAttribute("ev1",ev1);
	        model.addAttribute("event", event);
	        return "participants";
	    }
	 
		@GetMapping("details-participant/{id}")
		public String detailsParticipant(@PathVariable ("id") long id, Model model) {
			  EventVisiter participant = this.repository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid candidate id : " + id));
			User user =participant.getParticipant();
	    	model.addAttribute("user", user);
			return "view-participant";
		}
		@GetMapping("details-event/{id}")
		public String detailsEvent(@PathVariable ("id") long id, Model model) {
		    EventVisiter participant = this.repository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
			long a = participant.getEventID();
			Event event = this.eventRepository.findById(a)
					.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
		
			model.addAttribute("event",event);
			return "interested-event";
		}
	
}