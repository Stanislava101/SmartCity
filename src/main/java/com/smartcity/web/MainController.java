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


import com.smartcity.model.Event;
import com.smartcity.model.FileDB;
import com.smartcity.model.Offer;
import com.smartcity.model.User;
import com.smartcity.model.UserData;
import com.smartcity.repository.FileDBRepository;
import com.smartcity.repository.UserDataRepository;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.FileStorageService;
import com.smartcity.service.UserDataService;
import com.smartcity.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserDataRepository repository;
	@Autowired
	UserService service;
	@Autowired
	UserDataService dataService;
	@Autowired
	private FileDBRepository fileDBRepository;
	@Autowired
	private FileStorageService storageService;
	
	private long myUserID;
		
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/")
	public String home(Model model, @Param("keyword") String keyword) {
		List<User> listUsers = service.userFound(keyword);
        model.addAttribute("users", listUsers);
        model.addAttribute("keyword", keyword);
		return "index";
	}
	
	@GetMapping("/page")
	public String page() {
		return "page";
	}
	
	@GetMapping("/foundUser")
	public String foundUser(Model model, @Param("keyword") String keyword, UserData offer) {
		List<User> listUsers = service.userFound(keyword);
		for(User e:listUsers) {
			e = this.userRepository.findById(e.getId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid data id"));
		}

        model.addAttribute("users", listUsers);
        model.addAttribute("keyword", keyword);
		return "filter-users";
	}
	@GetMapping("add-details/{id}")
	public String details(@PathVariable ("id") long id,UserData userData, Model model) {
		UserData dataID = this.repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid data id : " + id));
		myUserID = id;
		model.addAttribute("data", dataID);
	//	model.addAttribute("event", event);
		return "add-data";
	}

	@PostMapping("addData/{id}") 
	public String addVisiter(@Valid UserData data, BindingResult result,@PathVariable ("id") long id, Model model) {
		if(result.hasErrors()) {
		return "add-data";
		
		}
		System.out.println("MyUserID = " + myUserID);
		UserData userData = this.repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid data id) : " + myUserID));
		User user = userData.getUser();
		data.setUser(user);
		repository.save(data);
		model.addAttribute("data", data);
		return "redirect:/";
	}
	
	@GetMapping("/profile")
	public String viewJobOffer(Model model, User user){
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
	 user = this.userRepository.findByEmail(username);
	 long userID = user.getId();

	User userDetails = this.userRepository.findById(userID)
				.orElseThrow(() -> new IllegalArgumentException("Invalid candidate id : "));
	UserData userData = user.getData();
	long idDescription = userData.getId();
		UserData offer = this.repository.findById(idDescription)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : "));


		FileDB userFile = this.fileDBRepository.findByUser(user);
		if(userFile == null) {
			return "redirect:fileN";
		}
		String fileID = userFile.getId();
		FileDB file= storageService.getFile(fileID);
		model.addAttribute("file",file);
		model.addAttribute("user", userDetails);
		model.addAttribute("data", offer);
		return "user-profile";
	}
	
	@GetMapping("view/{id}")
	public String showEvent(@PathVariable ("id") long id, Model model, User user) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
	 user = this.userRepository.findByEmail(username);
	 long userID = user.getId();

	User userDetails = this.userRepository.findById(userID)
				.orElseThrow(() -> new IllegalArgumentException("Invalid candidate id : "));
	UserData userData = user.getData();
	long idDescription = userData.getId();
		UserData offer = this.repository.findById(idDescription)
				.orElseThrow(() -> new IllegalArgumentException("Invalid offer id : "));
		FileDB userFile = this.fileDBRepository.findByUser(user);
		String fileID = userFile.getId();
		FileDB file= storageService.getFile(fileID);
		model.addAttribute("file",file);
		model.addAttribute("user", userDetails);
		model.addAttribute("data", offer);
		return "view-user";
	}

}