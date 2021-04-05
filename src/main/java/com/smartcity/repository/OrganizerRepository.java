package com.smartcity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartcity.model.Event;
import com.smartcity.model.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long>{
	List<Organizer> findByName(String name);
	
	@Query("SELECT p FROM Event p WHERE p.name LIKE %?1%" + "OR p.desc LIKE %?1%")
	public List<Event> findAll(String keyword);

}
