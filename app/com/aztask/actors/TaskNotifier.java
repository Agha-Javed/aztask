package com.aztask.actors;

import java.util.Collections;
import java.util.List;

import com.aztask.business.Task;
import com.aztask.service.UserService;
import com.aztask.vo.ActorReply;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

import play.Logger.ALogger;
import akka.actor.UntypedActor;

public class TaskNotifier extends UntypedActor {

	public final static ALogger log=play.Logger.of(TaskNotifier.class);
	
	public TaskNotifier() {
	}// end constructor
	
	@Override
	public void onReceive(final Object obj){
			log.info("Notifying all consumers.");
			ActorReply actorReply = (ActorReply) obj;
			int taskId = actorReply.getTaskId();
			log.info("Retrieving task from DB:" + taskId);
			TaskVO taskVO = new Task().getTaskById(taskId);
			log.info("Task VO:" + taskVO);
			List<UserVO> nearbyUsers = getNearbyUsers(taskVO);
			log.info("Nearby Users:"+nearbyUsers);
	}

	public List<UserVO> getNearbyUsers(TaskVO taskVO){
		if(taskVO!=null){
			return UserService.getInstance().nearByUsers(taskVO);
			
		}
		return Collections.emptyList();
	}

}// end class
