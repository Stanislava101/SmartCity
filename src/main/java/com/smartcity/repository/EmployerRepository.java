package com.smartcity.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.smartcity.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long>{
	List<Employer> findByName(String name);
	
	@Query("SELECT p FROM Employer p WHERE p.name LIKE %?1%")
	public List<Employer> findAll(String keyword);
}
