package com.aztask.actors;

import play.libs.Akka;

import com.aztask.vo.TaskVO;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TaskSupervisor extends UntypedActor{

	private final LoggingAdapter log = Logging.getLogger(context().system(),this);

	public TaskSupervisor() {}
	
	@Override
	public void onReceive(Object obj) throws Exception {
		if(obj instanceof TaskVO){
	    	ActorRef taskNotifier = Akka.system().actorOf(Props.create(TaskNotifier.class));
	    	log.info("Sending task to Child:");
	    	taskNotifier.tell((TaskVO)obj, self());
		}else if(obj instanceof String){
			log.info("Got reply from Child:"+obj);
			ActorRef actorRef=sender();
			log.info("Got reply from Child:"+actorRef.path().name());
			actorRef.tell(PoisonPill.getInstance(), null);
		}else{
			log.info("received unknown message");
		}
	}
	
	
}
