package com.smartcity.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcity.model.User;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.UserService;
import com.smartcity.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/users/")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("list")
	public String users(Model model,@Param("keyword") String keyword) {
		List<User> listUsers = userService.listUser(keyword);
		model.addAttribute("users", listUsers);
		model.addAttribute("keyword", keyword);
		return "list-users";
	}
	@GetMapping("view-user/{id}")
	public String viewUser(@PathVariable ("id") long id, Model model) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + id));
		
		model.addAttribute("user", user);
		return "view-userinfo";
	}

	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + id));
		model.addAttribute("user", user);
		return "update-user";
	}
	
	@PostMapping("update/{id}")
	public String updateUser(@PathVariable("id") long id,@ModelAttribute("user") UserRegistrationDto registrationDto, Model model) {
		User updateUser = userRepository.getOne(id);
		updateUser.setFirstName(registrationDto.getFirstName());
		updateUser.setLastName(registrationDto.getLastName());
		updateUser.setEmail(registrationDto.getEmail());
		updateUser.setPhoneNo(registrationDto.getPhoneNo());
		userRepository.save(updateUser);
		model.addAttribute("users",this.userRepository.findAllUsers());
		return "list-users";
	}
	
	
}
