package com.aztask.actors;

import java.util.Collections;
import java.util.List;

import com.aztask.business.Task;
import com.aztask.business.User;
import com.aztask.vo.ActorReply;
import com.aztask.vo.NearbyUser;
import com.aztask.vo.TaskVO;

import play.Logger.ALogger;
import akka.actor.UntypedActor;
import play.db.jpa.JPA;
import play.libs.F;

public class TaskNotifier extends UntypedActor {

	public final static ALogger log=play.Logger.of(TaskNotifier.class);
	
	private String name;

	public TaskNotifier() {
	}// end constructor
	
	
	public TaskNotifier(String name){
		this.name=name;
	}
	
	
	@Override
	public void onReceive(final Object obj) throws Exception {
		log.info("Received Task from supervisor:"+sender().path().name());
		log.info("Notifying all consumers.");
		
		try {
			JPA.withTransaction(new F.Callback0() {
				@Override
				public void invoke() throws Throwable {
					log.info("Sending notifications to all users.");
					ActorReply actorReply=(ActorReply)obj;
					int taskId=actorReply.getTaskId();
					log.info("Retrieving task from DB:"+taskId);
					TaskVO taskVO=new Task().getTaskById(taskId);
					log.info("Task VO:"+taskVO);
					List<NearbyUser> nearbyUsers=getNearbyUsers(taskVO);
					log.info("Got Nearby Users:"+nearbyUsers);
				}
			});
		} catch (Throwable e) {
            throw new Exception(e);
        }
	}
	
	public List<NearbyUser> getNearbyUsers(TaskVO taskVO){
		
		List<NearbyUser> nearbyUserList=null;
		if(taskVO!=null){
			return new User().getNearbyUsers(taskVO);
		}
		return Collections.emptyList();
	}

}// end class
