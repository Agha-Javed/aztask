package com.aztask.service;

import play.Logger.ALogger;
import play.libs.Akka;
import akka.actor.ActorSelection;

import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.Reply;
import com.aztask.vo.TaskVO;

public class TaskService {

	public static ALogger task_service_log=play.Logger.of(TaskService.class);

	private static TaskService taskService;
	
	private TaskService(){}
	
	public Reply createTask(TaskVO task){
		task_service_log.info("TaskService: creating task.");
    	ActorSelection parentActor = Akka.system().actorSelection("/user/ParentActor");
    	parentActor.tell(task, parentActor.anchor());
    	return new Reply("200", "Success");
	}
	
	
	public Reply acceptTask(AcceptedTaskVO acceptedTaskVO){
		task_service_log.info("Accepting task"+acceptedTaskVO);
    	ActorSelection parentActor = Akka.system().actorSelection("/user/ParentActor");
    	parentActor.tell(acceptedTaskVO, parentActor.anchor());
    	return new Reply("200", "Success");
	}

	synchronized public static TaskService getInstance(){
		task_service_log.info("Initializing TaskService");
		if(taskService==null){
			taskService=new TaskService();
		}
		return taskService;
	}	
	
}
