package com.smartcity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcity.model.Message;
import com.smartcity.model.UserApplication;
import com.smartcity.repository.MessageRepository;

@Service
public class MessageService {
	@Autowired
	MessageRepository messageRepository;

    public void saveMessage(Message entity, UserApplication application) {
			entity.setApplication(application);
			entity = messageRepository.save(entity);
		}
    
}
