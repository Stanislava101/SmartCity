package com.smartcity.service;


import java.util.Arrays;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartcity.model.Role;
import com.smartcity.model.User;
import com.smartcity.repository.UserRepository;
import com.smartcity.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto){
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getPhoneNo(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
	
		return userRepository.save(user);
	}
	
	
	@Override
	public User saveNewUser(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(),
				registrationDto.getLastName(), registrationDto.getPhoneNo(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_ADMIN")));
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}


	@Override
	public User saveEmployer(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getPhoneNo(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_EMPLOYER")));
		
		return userRepository.save(user);
	}

	@Override
	public User saveOrganizer(UserRegistrationDto registrationDto) {
		
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getPhoneNo(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_ORGANIZER")));
		
		return userRepository.save(user);
	}

	@Override
	public Long getUserID(User user) {
		return user.getId();
	}


    public List<User> userFound(String keyword) {
        if (keyword != null) {
            return userRepository.search(keyword);
       }
       return userRepository.findAll();
}
		
	    public List<User> listAll(String keyword) {
	        if (keyword != null) {
	            return userRepository.findAll(keyword);
	       }
	       return userRepository.findAll();
   }

		@Override
		public List<User> listEm(String keyword) {
	        if (keyword != null) {
	            return userRepository.findAll(keyword);
	       }
			return userRepository.findEm2();
		}
		
		@Override
		public List<User> listEm2() {
			return userRepository.findEm2();
		}


		@Override
		public List<User> listOrg(String keyword) {
	        if (keyword != null) {
	            return userRepository.findAllOrganizers(keyword);
	       }
			return userRepository.findOrg2();
		}

		@Override
		public List<User> listOrg2() {
			return userRepository.findOrg2();
		}

		@Override
		public List<User> listUser(String keyword) {
	        if (keyword != null) {
	            return userRepository.findSpecificUsers(keyword);
	       }
			return userRepository.findAllUsers();
		}		
		@Override
		public List<User> listUs(){
			return userRepository.findAllUsers();
		}
	}