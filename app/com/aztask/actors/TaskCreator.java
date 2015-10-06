package com.aztask.actors;

import play.Logger.ALogger;
import com.aztask.business.Task;
import com.aztask.vo.ActorReply;
import com.aztask.vo.TaskVO;
import akka.actor.UntypedActor;

/**
 * @author Javed
 * 
 *         This actor will create task into DB
 *
 */
public class TaskCreator extends UntypedActor {

	public final static ALogger log = play.Logger.of(TaskCreator.class);

	public TaskCreator() {
	}

	@Override
	public void onReceive(final Object task) throws Exception {
		log.info("Received Task from supervisor:" + sender().path().name());
		log.info("Saving Task into DB");

		try {
			int taskId = new Task().createTask((TaskVO) task);
			ActorReply reply = null;
			// If taskId is greater than 0, that means task has been created.
			if (taskId > 0) {
				reply = new ActorReply(true, taskId);
			}
			sender().tell(reply, self());
		} catch (Throwable e) {
			throw new Exception(e);
		}

	}
}
