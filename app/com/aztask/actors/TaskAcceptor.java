package com.aztask.actors;

import com.aztask.business.User;
import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.UserVO;
import play.Logger.ALogger;
import play.db.jpa.JPA;
import play.libs.F;
import akka.actor.UntypedActor;

public class TaskAcceptor extends UntypedActor {

	public final static ALogger log = play.Logger.of(TaskAcceptor.class);

	@Override
	public void onReceive(final Object obj) throws Exception {
		// TODO 1. This actor will make an entry into User's accepted tasks.
		// TOOD 2. Then it will search the task creator
		// TODO 3. lastely it will send notification to task creator, having all
		// details of this consumer.

		try {
			JPA.withTransaction(new F.Callback0() {
				@Override
				public void invoke() throws Throwable {
					AcceptedTaskVO acceptedTask = (AcceptedTaskVO) obj;
					log.info("" + acceptedTask);
					int taskId = acceptedTask.getTaskId();
					UserVO userVO = new User().userByTaskId(taskId);
					if (userVO != null) {
						log.info("Fetched producer:" + userVO);
					}
					sender().tell("Success", self());
				}
			});
		} catch (Throwable e) {
			throw new Exception(e);
		}

	}

}
