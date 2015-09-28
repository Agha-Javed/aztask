package com.aztask.service;

import akka.actor.ActorSelection;

import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.Reply;

import play.Logger.ALogger;
import play.libs.Akka;

public class UserService {

	public static ALogger user_service_log=play.Logger.of(UserService.class);

	private static UserService userService;

	
	public Reply acceptTask(AcceptedTaskVO acceptedTaskVO){
		user_service_log.info("Accepting task"+acceptedTaskVO);
    	ActorSelection parentActor = Akka.system().actorSelection("/user/ParentActor");
    	parentActor.tell(acceptedTaskVO, parentActor.anchor());
    	return new Reply("200", "Success");
	}
	
	synchronized public static UserService getInstance(){
		user_service_log.info("Initializing TaskService");
		if(userService==null){
			userService=new UserService();
		}
		return userService;
	}	

}
