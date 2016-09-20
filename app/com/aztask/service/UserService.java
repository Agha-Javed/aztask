package com.aztask.service;


import java.util.List;

import com.aztask.business.UserBO;
import com.aztask.vo.Login;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;
import com.aztask.vo.User;

import play.Logger.ALogger;

public class UserService {

	public static ALogger user_service_log=play.Logger.of(UserService.class);

	private static UserService userService;

	
	public String registerUser(User user){
    	return new UserBO().registerUser(user);
	}

	public Reply login(Login loginCredentials){
		return null;
	}

	//TODO I have to update this method
	public Reply updateUserProfile(User user){
    	return new UserBO().updateUserProfile(user);
	}

	
	public String isUserRegistered(String deviceId){
		return new UserBO().isUserRegistered(deviceId);
	}
	
	
	public List<User> nearByUsers(Task taskVO){
		return new UserBO().nearByUsers(taskVO);
	}
	
	
	synchronized public static UserService getInstance(){
		user_service_log.info("Initializing UserService");
		if(userService==null){
			userService=new UserService();
		}
		return userService;
	}	

}
