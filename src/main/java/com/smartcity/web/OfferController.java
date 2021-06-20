package com.smartcity.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcity.model.FileDB;
import com.smartcity.model.Offer;
import com.smartcity.model.User;
import com.smartcity.model.UserApplication;
import com.smartcity.repository.FileDBRepository;
import com.smartcity.repository.OfferRepository;
import com.smartcity.repository.UserApplicationRepository;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.FileStorageService;
import com.smartcity.service.OfferService;

@Controller
@RequestMapping("/offers/")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;	
	@Autowired
	private UserApplicationRepository repository;	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferService service;
	
	  @Autowired
	  private FileStorageService storageService;
	  @Autowired
	  private FileDBRepository fileDBRepository;
	
	private long userID;
//	private long candidateID = (long)1;
	
	
	@GetMapping("showForm")
	public String showForm(Offer offer) {
		return "add-joboffer";
	}
	
	@GetMapping("AddOffer")
	public String addOfferEmployer(Offer offer) {
		return "add-joboffer-employer";
	}
	
	@RequestMapping("list")
	public String list(Model model, @Param("keyword") String keyword) {
        List<Offer> listOffers = service.listAllOffers(keyword);
        model.addAttribute("offers", listOffers);
        model.addAttribute("keyword", keyword);
		return "list-joboffers";
	}
	
	
	@RequestMapping("allJobOffers")
	public String allOffers(Model model, @Param("keyword") String keyword) {
        List<Offer> listOffers = service.listAllUsers(keyword);
        model.addAttribute("offers", listOffers);
        model.addAttribute("keyword", keyword);
		return "list-offers-users";
	}
	
	@GetMapping("view-joboffer/{id}")
	public String addOfferToProfile(@PathVariable ("id") long id,Model model) {
		Offer offer = this.offerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));

		model.addAttribute("offers", this.offerRepository.findAll());
		userID = id;
		model.addAttribute("offer", offer);
		return "view-joboffer-users";
	}
	
	@GetMapping("view-events")
	public String userEventsList(Model model) {
		model.addAttribute("offers", this.offerRepository.findAll());
		return "view-events";
	}
	
	@PostMapping("add")
	public String add(@Valid Offer offer, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-joboffer";
		
		}

		this.offerRepository.save(offer);
		return "redirect:list";
	}
	
	@PostMapping("addOffer")
	public String addOfferByEmployer(@Valid Offer offers, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-joboffer-employer";
		
		}
		
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
		System.out.println(username);
		 offers.setEmployer(username);
		this.offerRepository.save(offers);
		return "redirect:filterOffers";
	}
	
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
	Offer offer = this.offerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
		
		model.addAttribute("offer", offer);
		return "update-joboffer";
	}
	
	@PostMapping("update/{id}")
	public String updateOffer(@PathVariable("id") long id, @Valid Offer offer, BindingResult result, Model model) {
		if(result.hasErrors()) {
			offer.setId(id);
			return "update-joboffer";
		}
		
		// update offer
		offerRepository.save(offer);
		
		// get all offers ( with update)
		model.addAttribute("offers", this.offerRepository.findAll());
		return "list-joboffers";
	}
	
	@GetMapping("editOffer/{id}")
	public String editOffer(@PathVariable ("id") long id, Model model) {
	Offer offer = this.offerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
		
		model.addAttribute("offer", offer);
		return "update-joboffer-employer";
	}
	
	@PostMapping("updateOffer/{id}")
	public String updateByEmployer(@PathVariable("id") long id, @Valid Offer offer, BindingResult result, Model model) {
		if(result.hasErrors()) {
			offer.setId(id);
			return "update-joboffer-employer";
		}
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
		System.out.println(username);
		 offer.setEmployer(username);
		// update offer
		offerRepository.save(offer);
		
		// get all offers ( with update)
		model.addAttribute("offers", this.offerRepository.findAllByEmp(username));
		return "filter-offers";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable ("id") long id, Model model) {
		
		Offer offer = this.offerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
		
		this.offerRepository.delete(offer);
		model.addAttribute("offers", this.offerRepository.findAll());
		return "list-joboffers";
		
	}
	
	@GetMapping("deleteOffer/{id}")
	public String deleteByEmployer(@PathVariable ("id") long id, Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
		
		Offer offer = this.offerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
		
		this.offerRepository.delete(offer);
		model.addAttribute("offers", this.offerRepository.findAllByEmp(username));
		return "filter-offers";
		
	}
	
	@GetMapping("view/{id}")
	public String view(@PathVariable ("id") long id, Model model) {
		
		Offer offer = this.offerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
		
	//	this.offerRepository.delete(offer);
		model.addAttribute("offers", this.offerRepository.findAll());
		return "page";
		
	}
	 @RequestMapping("/filterOffers")
	    public String viewFilteredOffers(Model model, @Param("keyword") String keyword) {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String email = ((UserDetails)principal).getUsername();
			 
		 List<Offer> listOffers = service.listAll(keyword);
	        model.addAttribute("offers", listOffers);
	        model.addAttribute("keyword", keyword);
	        return "filter-offers";
	    }
	 
		@GetMapping("view-offers/{id}")
		public String viewOffer(@PathVariable ("id") long id, Model model) {
			Offer offer = this.offerRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
			
			model.addAttribute("offer", offer);
			return "view-offer";
		}
		
		@GetMapping("view-myoffer/{id}")
		public String viewJobOffer(@PathVariable ("id") long id, Model model) {
			Offer offer = this.offerRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
			
			model.addAttribute("offer", offer);
			return "view-myoffer";
		}
	     
		@PostMapping("added")
		public String addOffer(@Valid UserApplication offers, BindingResult result, Model model) {
			if(result.hasErrors()) {
				return "view-joboffer-users";
			
			}
			
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 String email = ((UserDetails)principal).getUsername();
			User user = this.userRepository.findByEmail(email);
			service.addOffer(offers,userID,user);
			
			return "redirect:allJobOffers";
		}
		 @RequestMapping("/appliedJobOffers")
		    public String viewAppliedOffers(Model model) {
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 String username = ((UserDetails)principal).getUsername();
		        List<UserApplication> listEvents = service.listAllAppliedOffers(username);
		        model.addAttribute("offers", listEvents);
		        return "applied-offers";
		    }
		 
			@GetMapping("details-offer/{id}")
			public String interestedOfferDetails(@PathVariable ("id") long id, Model model) {
				UserApplication offer = this.repository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
				long a = offer.getOfferID();
				Offer theOffer = this.offerRepository.findById(a)
						.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : " + id));
			
				model.addAttribute("offer", offer);
				model.addAttribute("theOffer",theOffer);
				return "interested-job";
			}
			
			@GetMapping("details-candidate/{id}")
			public String detailsCandidate(@PathVariable ("id") long id, Model model) {
				  UserApplication application = this.repository.findById(id)
							.orElseThrow(() -> new IllegalArgumentException("Invalid candidate id : " + id));
				User user =application.getClient();
				//  long clientId = ((Number) a).longValue();
				//User user = this.userRepository.findById(id)
				//		.orElseThrow(() -> new IllegalArgumentException("Invalid candidate id : " + id));
				FileDB userFile = this.fileDBRepository.findByUser(user);
				String fileID = userFile.getId();
				FileDB file= storageService.getFile(fileID);
				model.addAttribute("fileUser", file);
				model.addAttribute("user", user);
				return "details-candidate";
			}
			
			 @RequestMapping("/candidates/{id}")
			    public String candidates(Model model, @PathVariable ("id") long id) {
				String filterID = String.valueOf(id);
				 List<UserApplication> listEvents = service.listAllByID(filterID);
			       model.addAttribute("offers", listEvents);
			        Offer offer = this.offerRepository.findById(id)
							.orElseThrow(() -> new IllegalArgumentException("Invalid candidate id : " + id));
			        long numCandidates = service.count(id);
			        model.addAttribute("numCandidates",numCandidates);
			        model.addAttribute("offer", offer);
			        return "candidates";
			    }
}
