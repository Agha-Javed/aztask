package com.aztask.actors;

import play.libs.Akka;

import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.ActorReply;
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
		if(obj instanceof Boolean){
	    	ActorRef creatorRef=Akka.system().actorOf(Props.create(TaskCreator.class,"TaskCreator"));
	    	log.info("Task Creator Actor has been created."+creatorRef.path());
	    	ActorRef notifierRef=Akka.system().actorOf(Props.create(TaskNotifier.class,"TaskNotifier"));
	    	log.info("Task Notifier Actor has been created."+notifierRef.path());
		}else if(obj instanceof TaskVO){
	    	ActorRef taskCreator = Akka.system().actorOf(Props.create(TaskCreator.class,"TaskCreator"));
	    	log.info("Task Creator Actor has been created."+taskCreator.path());
	    	log.info("Sending task to Child:");
	    	taskCreator.tell((TaskVO)obj, self());
		}else if(obj instanceof String){
			log.info("Got reply from Child:"+obj);
			ActorRef actorRef=sender();
			log.info("Got reply from Child:"+actorRef.path().name());
			actorRef.tell(PoisonPill.getInstance(), null);
		}else if(obj instanceof ActorReply){
			if( ( (ActorReply)obj ).isSuccess()){
				log.info("The task have been saved, Sending Notifications to All.");
		    	ActorRef taskNotifier = Akka.system().actorOf(Props.create(TaskNotifier.class));
		    	log.info("Task Creator Actor has been created."+taskNotifier.path());
		    	taskNotifier.tell(obj,self());
			}
		}else if(obj instanceof AcceptedTaskVO){
			log.info("The task has been accepted by User.");
	    	ActorRef taskAcceptor = Akka.system().actorOf(Props.create(TaskAcceptor.class));
	    	log.info("Task Acceptor Actor has been created."+taskAcceptor.path());
	    	taskAcceptor.tell(obj,self());
		}else{
			log.info("received unknown message");
		}
	}
	
	
}
