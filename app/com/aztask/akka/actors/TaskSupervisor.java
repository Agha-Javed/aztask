package com.aztask.akka.actors;

import com.aztask.vo.Task;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TaskSupervisor extends UntypedActor{

	private final LoggingAdapter log = Logging.getLogger(context().system(),this);

	public TaskSupervisor() {}
	
	
	@Override
	public void onReceive(Object obj) throws Exception {
		if (obj instanceof Task) {
			log.info("The task have been saved, Sending Notifications to all related users.");
			ActorRef notifierRef = getContext().actorOf(Props.create(TaskNotifier.class));
			log.info("Task Notifier Actor has been created."+ notifierRef.path());
			notifierRef.tell(obj, self());

		}
	}
	
	
}
