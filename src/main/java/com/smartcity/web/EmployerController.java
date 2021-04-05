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
import com.smartcity.repository.EmployerRepository;
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
	private EmployerService service;
	
	@Autowired
	private UserService userService;
	
	public EmployerController(UserService userService) {
		super();
		this.userService = userService;
	}
	
/*	
	 @RequestMapping("list")
	public String employers(Model model,@Param("keyword") String keyword) {
		 List<User> listEmployers = userService.listAll(keyword);
	    model.addAttribute("employers", listEmployers);
		model.addAttribute("keyword", keyword);
		return "list-employers";
	}
	 
	*/	@GetMapping("list")
		public String students(Model model,@Param("keyword") String keyword) {
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
	public String updateEmployer(@PathVariable("id") long id, @Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult result, Model model,User emp) {
		if(result.hasErrors()) {
			emp.setId((long)199);
			return "update-employer";
		}
		
		// update employer
		//userRepository.save(employer);
		//user.setId(id);
		emp.setId((long)199);
		emp= userService.updateEmployer(registrationDto);
		
		// get all employers ( with update)
		model.addAttribute("employers",userService.listEm2());
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
	
	 @RequestMapping("/filterEmployers")
	    public String viewFilteredEmployers(Model model, @Param("keyword") String keyword) {
	    //    List<Employer> listEmployers = service.listAll(keyword);
	     //   model.addAttribute("employers", listEmployers);
	      //  model.addAttribute("keyword", keyword);
			List<User> listEm = userService.listEm(keyword);
			model.addAttribute("employers", listEm);
			model.addAttribute("keyword", keyword);  
		 return "filter-employers";
	    }
	 
	 
	 
		@GetMapping("view/{id}")
		public String showUpdateForm3(@PathVariable ("id") long id, Model model) {
			Employer emp = this.employerRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
			
			model.addAttribute("event", emp);
			return "page";
		}
	/*	@GetMapping("view-employer/{id}")
		public String viewEmployer(@PathVariable ("id") long id, Model model) {
			Employer emp = this.employerRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid employer id : " + id));
			
			model.addAttribute("employer", emp);
			return "view-employers";
		}*/
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
		public String addEmployer(@ModelAttribute("user") UserRegistrationDto registrationDto) {
			userService.saveEmployer(registrationDto);
			return "redirect:/employers/add-employer?success";
		}
}