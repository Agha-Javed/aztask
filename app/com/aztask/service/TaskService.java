package com.aztask.service;

import java.util.List;

import play.Logger.ALogger;
import play.libs.Akka;
import akka.actor.ActorSelection;

import com.aztask.business.Task;
import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.NearByDevice;
import com.aztask.vo.Reply;
import com.aztask.vo.TaskVO;

public class TaskService {

	public static ALogger task_service_log=play.Logger.of(TaskService.class);

	private static TaskService taskService;
	
	private TaskService(){}
	
	/**
	 * This method will return all tasks regardless of users
	 * @return
	 */
	public List<TaskVO> newTasks(){
		return new Task().newTasks();
	}
	
	public List<TaskVO> featuredTasks(){
		return new Task().featuredTasks();
	}
	
	public List<TaskVO> nearByTasks(NearByDevice nearByLocation){
		return new Task().nearByTasks(nearByLocation);
	}
	
	
	public List<TaskVO> allTasksOfUser(int userId){
		return new Task().allTasksOfUser(userId);
	}
	
	public List<TaskVO> pendingTasksOfUser(int userId){
		return new Task().pendingTasksOfUser(userId);
	}
	
	public List<TaskVO> acceptedTasksOfUser(int userId){
		return new Task().acceptedTasksOfUser(userId);
	}
	
	public Reply createTask(TaskVO task){
		task_service_log.info("TaskService: creating task.");
    	ActorSelection parentActor = Akka.system().actorSelection("/user/ParentActor");
    	parentActor.tell(task, parentActor.anchor());
    	return new Reply("200", "Success");
	}
	
	public Reply deleteTask(int userId,int taskId){
		return null;
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
