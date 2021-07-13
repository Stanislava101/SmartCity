package com.smartcity.web;


import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smartcity.model.Employer;
import com.smartcity.model.User;
import com.smartcity.model.UserData;
import com.smartcity.repository.EmployerRepository;
import com.smartcity.repository.UserDataRepository;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.EmployerService;
import com.smartcity.service.UserService;
import com.smartcity.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/employers/")
public class EmployerController {

	@Autowired
	private EmployerRepository employerRepository;	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	UserDataRepository repository;
	
	@Autowired
	private EmployerService service;
	
	@Autowired
	private UserService userService;
	
	private long myUserID;
	
	public EmployerController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("list")
		public String employers(Model model,@Param("keyword") String keyword) {
			List<User> listEm = userService.listEm(keyword);
			model.addAttribute("employers", listEm);
			model.addAttribute("keyword", keyword);
			return "list-employers";
		}
	
	
	@GetMapping("listingEmployers")
	public String employersViewAdmin(Model model) {
		model.addAttribute("employers", this.employerRepository.findAll());
		return "admin-employerslist-view";
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto update() {
        return new UserRegistrationDto();
    }
	
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
		User employer = this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
		model.addAttribute("employer", employer);
		return "update-employer";
	}
	
	@PostMapping("update/{id}")
	public String updateEmployer(@PathVariable("id") long id,@ModelAttribute("user") UserRegistrationDto registrationDto, Model model) {
		User updateEmployer = userRepository.getOne(id);
		updateEmployer.setFirstName(registrationDto.getFirstName());
		updateEmployer.setLastName(registrationDto.getLastName());
		updateEmployer.setEmail(registrationDto.getEmail());
		updateEmployer.setPhoneNo(registrationDto.getPhoneNo());
		userRepository.save(updateEmployer);
		model.addAttribute("employers",this.userRepository.findEm2());
		return "list-employers";
	}
	
	@GetMapping("delete/{id}")
	public String deleteEmployer(@PathVariable ("id") long id, Model model) {
		
		User employer = this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
		
		this.userRepository.delete(employer);
		model.addAttribute("employers", userService.listEm2());
		return "list-employers";
		
	}
	
	
	@GetMapping("deleteUser/{id}")
	public String deleteUser(@PathVariable ("id") long id, Model model) {
		
		User employer = this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
		
		this.userRepository.delete(employer);
		return "list-users";
		
	}
	
	
	
	 @RequestMapping("/filterEmployers")
	    public String viewFilteredEmployers(Model model, @Param("keyword") String keyword) {
			List<User> listEm = userService.listEm(keyword);
			model.addAttribute("employers", listEm);
			model.addAttribute("keyword", keyword);  
		 return "filter-employers";
	    }
	 
	 
/*	 
		@GetMapping("view/{id}")
		public String showData(@PathVariable ("id") long id, Model model) {
			Employer emp = this.employerRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
			
			model.addAttribute("employer", emp);
			return "page";
		}
	*/
		@GetMapping("view-employer/{id}")
		public String viewEmployer(@PathVariable ("id") long id, Model model) {
			User emp = this.userRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
			
			model.addAttribute("employer", emp);
			return "view-employers";
		}
		@ModelAttribute("user")
	    public UserRegistrationDto userRegistrationDto() {
	        return new UserRegistrationDto();
	    }
		
		@GetMapping("add-employer")
		public String registerEmployer() {
			return "add-employer";
		}
		
		@PostMapping("add-employer")
		public String addEmployer(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,  BindingResult result) throws Exception {
			if(result.hasErrors()) {
				return "add-employer";
			}
			userService.saveEmployer(registrationDto);
			return "redirect:/employers/add-employer?success";
		}
		
		
}