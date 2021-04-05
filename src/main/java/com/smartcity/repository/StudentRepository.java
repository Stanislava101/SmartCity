package com.smartcity.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	List<Student> findByName(String name);
}