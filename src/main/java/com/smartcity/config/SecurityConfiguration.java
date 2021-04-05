package com.smartcity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.smartcity.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				 "/registration**",
				 "/register**",
	                "/js/**",
	                "/css/**",
	                "/img/**").permitAll()
		 .antMatchers("/page/**").access("hasRole('ROLE_ADMIN')")
		 .antMatchers("/employers/list").access("hasRole('ROLE_ADMIN')") 
		 .antMatchers("/employers/add-employer").access("hasRole('ROLE_ADMIN')") 
		 .antMatchers("/organizers/add-organizer").access("hasRole('ROLE_ADMIN')") 
		 .antMatchers("/organizers/list").access("hasRole('ROLE_ADMIN')") 
		 .antMatchers("/events/list").access("hasRole('ROLE_ADMIN')") 
		 .antMatchers("/offers/view-offers/**").access("hasRole('ROLE_ADMIN')")
		 .antMatchers("/organizers/listOrganizerEvent").access("hasRole('ROLE_ORGANIZER')")
		 .antMatchers("/organizers/showForm").access("hasRole('ROLE_ORGANIZER')")
		 .antMatchers("//organizers/editEvent/**").access("hasRole('ROLE_ORGANIZER')")
	//	 .antMatchers("/events/showForm").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')") 
	//	 .antMatchers("/organizers/showForm").access("hasRole('ROLE_ORGANIZER')") 
	//	 .antMatchers("/offers/**").access("hasRole('ROLE_EMPLOYER')") 
    	 .antMatchers("/offers/candidates/**").access("hasRole('ROLE_EMPLOYER')") 
    	 .antMatchers("/offers/AddOffer").access("hasRole('ROLE_EMPLOYER')") 
    	 .antMatchers("/offers/editOffer/**").access("hasRole('ROLE_EMPLOYER')") 
    	// .antMatchers("/offers/view-offers/**").access("hasRole('ROLE_EMPLOYER')") 
    	 .antMatchers("/offers/deleteOffer/**").access("hasRole('ROLE_EMPLOYER')") 
    	 .antMatchers("/offers/allJobOffers").access("hasRole('ROLE_USER')") 
    	 .antMatchers("/offers/view-joboffer/**").access("hasRole('ROLE_USER')") 
    	 .antMatchers("/offers/list").access("hasRole('ROLE_ADMIN')") 
    	 .antMatchers("/offers/filterOffers").access("hasRole('ROLE_EMPLOYER')") 
    	 .antMatchers("/offers/view-myoffer/**").access("hasRole('ROLE_EMPLOYER')") 
		 .antMatchers("/events/event-details/**").access("hasRole('ROLE_ADMIN')")
    	 .antMatchers("/offers/delete/**").access("hasRole('ROLE_ADMIN')") 
		 .antMatchers("/employers/filterEmployers").access("hasRole('ROLE_USER')") 
		 .antMatchers("/events/filterEvents").access("hasRole('ROLE_USER')") 
		 .antMatchers("/events/my-profile").access("hasRole('ROLE_USER')") 
		 .antMatchers("/events/view-event/**").access("hasRole('ROLE_USER')") 
		 .antMatchers("/events/interested-event/**").access("hasRole('ROLE_USER')")
		 .antMatchers("/events/AttendEvent/**").access("hasRole('ROLE_USER')")
		 .antMatchers("offers/appliedJobOffers").access("hasRole('ROLE_USER')")
		 .antMatchers("/students/**").access("hasRole('ROLE_ADMIN')") 
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}

}
