package com.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
