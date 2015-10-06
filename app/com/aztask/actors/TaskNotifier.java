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

public class TaskNotifier extends UntypedActor {

	public final static ALogger log=play.Logger.of(TaskNotifier.class);
	
	public TaskNotifier() {
	}// end constructor
	
	@Override
	public void onReceive(final Object obj) throws Exception {
		log.info("Received Task from supervisor:" + sender().path().name());
		log.info("Notifying all consumers.");

		try {
			log.info("Sending notifications to all users.");
			ActorReply actorReply = (ActorReply) obj;
			int taskId = actorReply.getTaskId();
			log.info("Retrieving task from DB:" + taskId);
			TaskVO taskVO = new Task().getTaskById(taskId);
			log.info("Task VO:" + taskVO);
			List<NearbyUser> nearbyUsers = getNearbyUsers(taskVO);
			log.info("Got Nearby Users:" + nearbyUsers);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}

	public List<NearbyUser> getNearbyUsers(TaskVO taskVO){
		if(taskVO!=null){
			return new User().getNearbyUsers(taskVO);
		}
		return Collections.emptyList();
	}

}// end class
