package com.smartcity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.smartcity.model.Offer;


	@Repository
	public interface OfferRepository extends JpaRepository<Offer, Long>{
		List<Offer> findByName(String name);
		@Query("SELECT p FROM Offer p WHERE p.name LIKE %?1%" + "OR p.desc LIKE %?1%")	
		public List<Offer> findAll(String keyword);
		@Query("SELECT p FROM Offer p WHERE p.employer=:employer")	
		public List<Offer> findAllByEmp(String employer);
		
	}
