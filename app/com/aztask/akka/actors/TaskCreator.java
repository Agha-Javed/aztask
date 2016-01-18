package com.aztask.akka.actors;

import play.Logger.ALogger;
import com.aztask.business.Task;
import com.aztask.vo.ActorReply;
import com.aztask.vo.TaskVO;
import akka.actor.UntypedActor;

/**
 * @author Javed
 * 
 */
public class TaskCreator extends UntypedActor {

	public final static ALogger log = play.Logger.of(TaskCreator.class);

	public TaskCreator() {
	}

	@Override
	public void onReceive(final Object task) throws Exception {
		log.info("Saving Task into DB");
		try {
			int taskId = new Task().createTask((TaskVO) task);
			ActorReply reply = null;
			if (taskId > 0) {
				reply = new ActorReply(true, taskId);
			}
			sender().tell(reply, self());
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}
}
