package com.aztask.service;

import java.util.List;
import play.Logger.ALogger;
import play.libs.Akka;
import akka.actor.ActorSelection;
import com.aztask.business.TaskBO;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;

public class TaskService {

	public static ALogger task_service_log=play.Logger.of(TaskService.class);

	private static TaskService taskService;
	
	private TaskService(){}
	
	/**
	 * This method will return all tasks regardless of users
	 * @return
	 */
	public List<Task> newTasks(){
		return new TaskBO().newTasks();
	}
	
	public List<Task> featuredTasks(){
		return new TaskBO().featuredTasks();
	}
	
	public List<Task> nearByTasks(String latitude, String longitude){
		return new TaskBO().nearByTasks(latitude,longitude);
	}
	
	
	public List<Task> allTasksOfUser(int userId){
		return new TaskBO().allTasksOfUser(userId);
	}
	
	public List<Task> pendingTasksOfUser(int userId){
		return new TaskBO().pendingTasksOfUser(userId);
	}
	
	public List<Task> tasksAcceptedByUser(int userId){
		return new TaskBO().tasksAcceptedByUser(userId);
	}
	
	public List<Task> tasksByCategories(String categories){
		return new TaskBO().tasksByCategories(categories);
	}
	
	public Reply createTask(Task task){
		task_service_log.info("TaskService: creating task.");
    	int taskId=new TaskBO().createTask(task);
    	if(taskId>0){
        	ActorSelection parentActor = Akka.system().actorSelection("/user/ParentActor");
        	parentActor.tell(task, parentActor.anchor());
    	}
    	return new Reply("200", "Success");
	}
	
	public Reply deleteTask(int userId,int taskId){
		boolean taskDeleted=false;
		if(userId>0 && taskId>0){
			task_service_log.info("TaskService: creating task.");
	    	taskDeleted=new TaskBO().deleteTask(userId, taskId);
		}
		
		return (taskDeleted) ? new Reply("200", "Success") : new Reply("204", "Failed") ;
	}
	
	public Reply acceptTask(int taskAcceptedBy , int taskId){
		task_service_log.info("Accepting task :"+taskId);
    	return (new TaskBO().acceptTask(taskAcceptedBy, taskId)) ? new Reply("200", "Success") : new Reply("404", "Failed") ;
	}

	synchronized public static TaskService getInstance(){
		task_service_log.info("Initializing TaskService");
		if(taskService==null){
			taskService=new TaskService();
		}
		return taskService;
	}	
	
}
