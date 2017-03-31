package com.aztask.akka.actors;

import java.util.Collections;
import java.util.List;
import com.aztask.business.TaskBO;
import com.aztask.service.UserService;
import com.aztask.util.Util;
import com.aztask.vo.Task;
import com.aztask.vo.User;
import play.Logger.ALogger;
import akka.actor.UntypedActor;

public class TaskNotifier extends UntypedActor {

	public final static ALogger log=play.Logger.of(TaskNotifier.class);
	
	public TaskNotifier() {
	}// end constructor
	
	@Override
	public void onReceive(final Object obj){
			Task taskVO =(Task)obj;
			log.info("Task VO:" + taskVO);
			List<User> nearbyUsers = getNearbyUsers(taskVO);
			log.info("Nearby Users:"+nearbyUsers);
			if(nearbyUsers!=null && nearbyUsers.size()>0){
				new TaskBO().assignTask(taskVO, nearbyUsers);
				//Task has been assigned to you.
				String message="You might like \""+Util.shortenTaskDesc(taskVO.getTask_desc())+"..\" task.";
				Util.sendAssignedNotifiction(nearbyUsers, taskVO);
			}
	}

	public List<User> getNearbyUsers(Task taskVO){
		if(taskVO!=null){
			return UserService.getInstance().nearByUsers(taskVO);
			
		}
		return Collections.emptyList();
	}
	
}// end class
