package com.smartcity.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
		
		@Transactional
		@Modifying
		@Query("delete from UserApplication where id = :id")
		void deleteApplication(@Param("id") long id);
		
		@Query("SELECT COUNT(id) FROM UserApplication where offerID=:offerID AND name=:name")
		public long checkApplication(long offerID, String name);


		
	}
