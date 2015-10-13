package com.aztask.service;


import com.aztask.business.User;
import com.aztask.vo.Login;
import com.aztask.vo.Reply;
import com.aztask.vo.UserVO;
import play.Logger.ALogger;

public class UserService {

	public static ALogger user_service_log=play.Logger.of(UserService.class);

	private static UserService userService;

	
	public Reply registerUser(UserVO user){
    	return new User().registerUser(user);

	}

	public Reply login(Login loginCredentials){
		return null;
	}

	//TODO I have to update this method
	public Reply updateUserProfile(UserVO user){
    	return new User().registerUser(user);
	}

	
	public Reply isUserRegistered(String deviceId){
		return new User().isUserRegistered(deviceId);
	}
	
	
	
	synchronized public static UserService getInstance(){
		user_service_log.info("Initializing UserService");
		if(userService==null){
			userService=new UserService();
		}
		return userService;
	}	

}
