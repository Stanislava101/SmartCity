package com.smartcity.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcity.model.FileDB;
import com.smartcity.model.User;
import com.smartcity.repository.FileDBRepository;
import com.smartcity.service.FileStorageService;
import com.smartcity.service.UserDataService;
import com.smartcity.service.UserService;
import com.smartcity.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;
	@Autowired
	private UserDataService service;
	
	@Autowired
	private FileStorageService storageService;
	
	@Autowired
	private FileDBRepository fileDBRepository;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			return "registration";
		}
		User user= userService.save(registrationDto);
		service.addMoreDetails(user);
		return "redirect:/registration?success";
	}
	
}
