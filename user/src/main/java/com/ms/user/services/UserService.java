package com.ms.user.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.user.dtos.UserRecordDto;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserProducer userProducer;
	
	@Transactional
	public UserModel save(UserRecordDto userRecordDto) {
		var userModel = new UserModel();
		BeanUtils.copyProperties(userRecordDto, userModel);
		userModel = userRepository.save(userModel);
		userProducer.publishMessageEmail(userModel);
		return userModel;
	}
}
