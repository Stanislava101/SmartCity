package com.smartcity.repository;

import java.util.List
;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.smartcity.model.Event;

	@Repository
	public interface EventRepository extends JpaRepository<Event, Long>{
		List<Event> findByName(String name);
		@Query("SELECT p FROM Event p WHERE p.name LIKE %?1%" + "OR p.desc LIKE %?1%")
		public List<Event> findAll(String keyword);
		
		@Query("SELECT p FROM Event p WHERE p.organizer=:organizer")
		public List<Event> findEventByOrg(String organizer);
		
		@Query("SELECT id FROM Event")
		public List<Event> findEventById(long l);
	}
