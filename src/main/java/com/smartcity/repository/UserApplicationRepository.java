package com.smartcity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartcity.model.UserApplication;

	@Repository
	public interface UserApplicationRepository extends JpaRepository<UserApplication, Long>{
		List<UserApplication> findByName(String name);
		
		@Query("SELECT p FROM UserApplication p WHERE p.name LIKE %?1%")
		public List<UserApplication> findAll(String keyword);
		
		@Query("SELECT p FROM UserApplication p WHERE CAST( p.offerID as string ) LIKE %?1% ")
		public List<UserApplication> findByID(String id);
		
		@Query("SELECT COUNT(offerID) FROM UserApplication WHERE offerID = :id")
		public long count(long id);
		
	}
