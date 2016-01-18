package com.aztask.akka.actors;

import play.libs.Akka;
import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.ActorReply;
import com.aztask.vo.TaskVO;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TaskSupervisor extends UntypedActor{

	private final LoggingAdapter log = Logging.getLogger(context().system(),this);

	public TaskSupervisor() {}
	
	
	@Override
	public void onReceive(Object obj) throws Exception {
		
		ACTOR_ACTIONS action=(ACTOR_ACTIONS)obj;
		
		switch (action) {
			case INITIALIZE:{
		    	/*
		    	 * This is the main part, where all child actors will be created, as of now we have two main child actors
		    	 * 
		    	 * TaskCreater , This child actor will save task into DB
		    	 * 
		    	 * TaskNotifier , Once task saved by TaskCreator, this actor will notify all related users.
		    	 */
				
				ActorRef creatorRef=getContext().actorOf(Props.create(TaskCreator.class),"TaskCreator");
		    	log.info("Task Creator Actor has been created."+creatorRef.path());

		    	ActorRef notifierRef=getContext().actorOf(Props.create(TaskNotifier.class),"TaskNotifier");
		    	log.info("Task Notifier Actor has been created."+notifierRef.path());
		    	
		    	ActorRef taskAcceptor = Akka.system().actorOf(Props.create(TaskAcceptor.class));
				log.info("Task Acceptor Actor has been created."+taskAcceptor.path());
			}
			

			case CREATE_JOB:{
				log.info("Recieved new task, delegating to TaskCreater worker.");
				ActorSelection taskCreator = Akka.system().actorSelection("/user/ParentActor/TaskCreator");
		    	taskCreator.tell((TaskVO)obj, self());
			}
		
		default:
			break;
		}
		
		if(obj instanceof Boolean){
	    	/*
	    	 * This is the main part, where all child actors will be created, as of now we have two main child actors
	    	 * 
	    	 * TaskCreater , This child actor will save task into DB
	    	 * 
	    	 * TaskNotifier , Once task saved by TaskCreator, this actor will notify all related users.
	    	 */
			
			ActorRef creatorRef=getContext().actorOf(Props.create(TaskCreator.class),"TaskCreator");
	    	log.info("Task Creator Actor has been created."+creatorRef.path());

	    	ActorRef notifierRef=getContext().actorOf(Props.create(TaskNotifier.class),"TaskNotifier");
	    	log.info("Task Notifier Actor has been created."+notifierRef.path());
	    	
	    	ActorRef taskAcceptor = Akka.system().actorOf(Props.create(TaskAcceptor.class));
			log.info("Task Acceptor Actor has been created."+taskAcceptor.path());

	    	
		}else if(obj instanceof TaskVO){
	    	
			log.info("Recieved new task, delegating to TaskCreater worker.");
			ActorSelection taskCreator = Akka.system().actorSelection("/user/ParentActor/TaskCreator");
	    	taskCreator.tell((TaskVO)obj, self());
	    	
		}else if(obj instanceof ActorReply){
			/**
			 * Once task has been created, a notifier needs to notify all related users.
			 */
			if( ( (ActorReply)obj ).isSuccess()){
				log.info("The task have been saved, Sending Notifications to all related users.");
		    	ActorSelection taskNotifier = Akka.system().actorSelection("/user/ParentActor/TaskNotifier");
		    	taskNotifier.tell(obj,self());
			}
			
		}else if(obj instanceof AcceptedTaskVO){
			log.info("The task has been accepted by User, delegating it to TaskAcceptor worker.");
	    	ActorSelection taskAcceptor = Akka.system().actorSelection("/user/ParentActor/TaskAcceptor");
	    	taskAcceptor.tell(obj,self());
		}else{
			/**
			 * Unattainted message should be saved into some kind of log table for later processing.
			 */
			log.info("received unknown message");
		}
	}
	
	
}
