package com.smartcity.repository;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.SQLQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcity.model.Employer;
import com.smartcity.model.User;
import com.smartcity.web.dto.UserRegistrationDto;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	
	//@Query("SELECT p FROM User p WHERE p.firstName LIKE %?1%") //filter
	@Query( "   from User e\r\n"
			+ "  inner join Role p on p.id = e.id\r\n"
			+ "where p.name = 'ROLE_EMPLOYER' and e.firstName LIKE %?1%")	
	public List<User> findAll(String keyword);
	
	//@Query("SELECT p FROM User p WHERE p.firstName='Stanislava'")

@Query( "   from User e\r\n"
		+ "  inner join Role p on p.id = e.id\r\n"
		+ "where p.name = 'ROLE_EMPLOYER'")	
public List<User> findEm(String keyword);

@Query( "   from User e\r\n"
		+ "  inner join Role p on p.id = e.id\r\n"
		+ "where p.name = 'ROLE_EMPLOYER'")	
public List<User> findEm2();


@Query( "   from User e\r\n"
		+ "  inner join Role p on p.id = e.id\r\n"
		+ "where p.name = 'ROLE_ORGANIZER' and e.firstName LIKE %?1%")	
public List<User> findAllOrganizers(String keyword);


@Query( "   from User e\r\n"
	+ "  inner join Role p on p.id = e.id\r\n"
	+ "where p.name = 'ROLE_ORGANIZER'")	
public List<User> findOrg(String keyword);

@Query( "   from User e\r\n"
	+ "  inner join Role p on p.id = e.id\r\n"
	+ "where p.name = 'ROLE_ORGANIZER'")	
public List<User> findOrg2();


}