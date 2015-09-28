package com.aztask.actors;

import play.Logger.ALogger;
import play.db.jpa.JPA;
import play.libs.F;
import com.aztask.business.Task;
import com.aztask.vo.ActorReply;
import com.aztask.vo.TaskVO;
import akka.actor.UntypedActor;

/**
 * @author Javed
 * 
 * This actor will create task into DB
 *
 */
public class TaskCreator extends UntypedActor{
	
	public final static ALogger log=play.Logger.of(TaskCreator.class);
	private String name;

	public TaskCreator() {
	}
	

	public TaskCreator(String name) {
		this.name=name;
	}

	@Override
	public void onReceive(final Object task) throws Exception {
		log.info("Received Task from supervisor:"+sender().path().name());
		log.info("Saving Task into DB");
		
		try {
			JPA.withTransaction(new F.Callback0() {
				@Override
				public void invoke() throws Throwable {
					int taskId=new Task().createTask((TaskVO)task);
					ActorReply reply=null;
					// If taskId is greater than 0, that means task has been created.
					if(taskId>0){
						reply=new ActorReply(true, taskId);
					}
					sender().tell(reply, self());
				}
			});
		} catch (Throwable e) {
            throw new Exception(e);
        }
		
	}
}
