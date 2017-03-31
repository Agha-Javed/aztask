package com.aztask.service;

import java.util.List;
import play.Logger.ALogger;
import play.libs.Akka;
import akka.actor.ActorSelection;
import com.aztask.business.TaskBO;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;
import com.fasterxml.jackson.databind.JsonNode;

public class TaskService {

	public static ALogger task_service_log=play.Logger.of(TaskService.class);

	private static TaskService taskService;
	
	private TaskService(){}
	
	/**
	 * This method will return all tasks regardless of users
	 * @return
	 */
	public String newTasks(){
		task_service_log.info("TaskService: listing new tasks.");
		return new TaskBO().newTasks();
	}
	
	public List<Task> featuredTasks(){
		return new TaskBO().featuredTasks();
	}
	
	public String nearByTasks(JsonNode nearByTasksRequestNode){
		
		return new TaskBO().nearByTasks(nearByTasksRequestNode);
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
			task_service_log.info("TaskService: deleting task.");
	    	taskDeleted=new TaskBO().deleteTask(userId, taskId);
		}
		
		return (taskDeleted) ? new Reply("200", "Success") : new Reply("204", "Failed") ;
	}
	
	public Object unAssignTask(int userId, int taskId) {

		boolean taskUnAssigned=false;
		if(userId>0 && taskId>0){
			task_service_log.info("TaskService: Un Assigning task.");
			taskUnAssigned=new TaskBO().unAssignTask(userId, taskId);
		}

		return (taskUnAssigned) ? new Reply("200", "Success") : new Reply("204", "Failed") ;
	}	

	
	public Reply acceptTask(int taskAcceptedBy , int taskId){
		task_service_log.info("Accepting task :"+taskId);
    	return (new TaskBO().acceptTask(taskAcceptedBy, taskId)) ? new Reply("200", "Success") : new Reply("404", "Failed") ;
	}

	public Reply likeTask(int userIdWhoLikedTask , int taskId){
		task_service_log.info("Accepting task :"+taskId);
    	return (new TaskBO().likeTask(userIdWhoLikedTask, taskId)) ? new Reply("200", "Success") : new Reply("404", "Failed") ;
	}

	public Reply unLikeTask(int userIdWhoUnLikedTask , int taskId){
    	return (new TaskBO().unLikeTask(userIdWhoUnLikedTask, taskId)) ? new Reply("200", "Success") : new Reply("404", "Failed") ;
	}

	
	synchronized public static TaskService getInstance(){
		task_service_log.info("Initializing TaskService");
		if(taskService==null){
			taskService=new TaskService();
		}
		return taskService;
	}

	public String assignedTasksToUser(int userId) {
		task_service_log.info("TaskService.assignedTasksToUser() :");
    	return new TaskBO().assignedTasksToUser(userId);
	}

	
}
