package com.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.model.FileDB;
import com.smartcity.model.User;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

	FileDB findByUser(User userID);

//	void save(FileDB fileDB, User user);

}