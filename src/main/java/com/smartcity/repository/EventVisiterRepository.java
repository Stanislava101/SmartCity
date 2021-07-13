package com.smartcity.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.smartcity.model.EventVisiter;
import com.smartcity.model.UserApplication;

	@Repository
	public interface EventVisiterRepository extends JpaRepository<EventVisiter, Long>{
		List<EventVisiter> findByName(String name);
		
		@Query("SELECT p FROM EventVisiter p WHERE p.name LIKE %?1%")
		public List<EventVisiter> findAll(String keyword);
		
		@Query("SELECT p FROM EventVisiter p WHERE CAST( p.eventID as string ) LIKE %?1% ")
		public List<EventVisiter> findByID(String id);
		
		@Query("SELECT COUNT(eventID) FROM EventVisiter WHERE eventID = :id")
		public int count(long id);
		
		@Query("SELECT COUNT(id) FROM EventVisiter where name=:name AND eventID=:eventID")
		public long checkVisiter(long eventID, String name);
	}

