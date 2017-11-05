package com.bsd.oms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsd.oms.entity.User;
import com.bsd.oms.repo.UserRepository;

@Component
public class LoginService {

	@Autowired
	private UserRepository userRepo;

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserDetails(String userName) {
		User user = userRepo.getUserByUserName(userName);
		return user;
	}
}
