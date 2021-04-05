package com.smartcity.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.model.User;
import com.smartcity.model.UserData;

	@Repository
	public interface UserDataRepository extends JpaRepository<UserData, Long>{

		void save(User user);

	//	 @Query("select u from userdata u where u.user_data = 32")
	//	  UserData findByUser(@Param("data") long data);
	}
