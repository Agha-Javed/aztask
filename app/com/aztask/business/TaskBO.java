package com.aztask.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import play.Logger.ALogger;
import play.libs.Json;
import com.aztask.data.TaskDao;
import com.aztask.data.UserDao;
import com.aztask.data.mybatis.TaskDaoImpl_MyBatis;
import com.aztask.data.mybatis.UserDaoImpl_MyBatis;
import com.aztask.util.Util;
import com.aztask.vo.AssignedTask;
import com.aztask.vo.LikedTask;
import com.aztask.vo.Task;
import com.aztask.vo.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TaskBO {

	public static ALogger Logger=play.Logger.of(TaskBO.class);
	
	public int createTask(Task task) {
		Logger.info("TaskBO.createTask.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		task.setTask_time(sdf.format(new Date(System.currentTimeMillis())));
		int taskId = taskDao.createTask(task);
		return taskId;
	}

	public boolean deleteTask(int userId,int taskId){
		Logger.info("TaskBO.deleteTask.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.deleteTask(userId,taskId);
	}
	
	public boolean unAssignTask(int userId, int taskId) {
		Logger.info("TaskBO.unAssignTask.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.unAssignTask(userId,taskId);
	}

	
	public String newTasks() {
		Logger.info("TaskBO.newTasks.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		ArrayNode tasks=new ArrayNode(JsonNodeFactory.instance);
		try {
			List<Task> newTasks=taskDao.newTasks();
			for (Task task : newTasks) {
					tasks.add(((ObjectNode)Json.toJson(task)).put("liked", "false"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Json.stringify(tasks);
	}
	
	public String assignedTasksToUser(int userId) {
		Logger.info("TaskBO.assignedTasksToUser().");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		ArrayNode tasks=new ArrayNode(JsonNodeFactory.instance);
		try {
			List<Integer> likedTaskIds=taskDao.getTasksLikedByUser(userId);
			List<Task> assignedTasks=taskDao.assignedTasksToUser(userId);
			
			for (Task task : assignedTasks) {
				if(likedTaskIds.contains(task.getTask_id())){
					tasks.add(((ObjectNode)Json.toJson(task)).put("liked", "true"));
				}else{
					tasks.add(((ObjectNode)Json.toJson(task)).put("liked", "false"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Json.stringify(tasks);
	}


	public List<Task> featuredTasks() {
		Logger.info("TaskBO.featuredTasks.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.featuredTasks();

	}

	public String nearByTasks(JsonNode nearByTasksRequestNode) {
		Logger.info("TaskBO.nearByTasks.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		
		int userId=nearByTasksRequestNode.get("userId").asInt();
		String latitude=nearByTasksRequestNode.get("latitude").asText();
		String longitude=nearByTasksRequestNode.get("longitude").asText();

		Logger.info("Latitude."+latitude);
		Logger.info("longitude."+longitude);

		List<Integer> taskIds=taskDao.getTasksLikedByUser(userId);
		List<Task> nearByTasks=taskDao.nearByTasks(latitude,longitude);
		
		Logger.info("JAVED::Tasks liked by this user."+taskIds);
		
		ArrayNode tasks=new ArrayNode(JsonNodeFactory.instance);
		for (Task task : nearByTasks) {
			if(taskIds.contains(task.getTask_id())){
				tasks.add(((ObjectNode)Json.toJson(task)).put("liked", "true"));
			}else{
				tasks.add(((ObjectNode)Json.toJson(task)).put("liked", "false"));
			}
		}
		return Json.stringify(tasks);//taskDao.nearByTasks(latitude,longitude);
	}

	public Task getTaskById(int taskId) {
		Logger.info("TaskBO.getTaskById.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.getTaskById(taskId);
	}

	public List<Task> allTasksOfUser(int userId) {
		Logger.info("TaskBO.allTasksOfUser.");
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.getTasksByUser(userId);
	}

	public List<Task> pendingTasksOfUser(int userId) {
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.pendingTasksOfUser(userId);
	}
	
	public List<Task> tasksAcceptedByUser(int userId){
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.acceptedTasksOfUser(userId);
	}
	
	public boolean acceptTask(int acceptedByUserId,int acceptedTaskId){
		Logger.info("acceptTask():: accepting task.");
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		Task task=taskDao.getTaskById(acceptedTaskId);
		AssignedTask assignedTaskVO=taskDao.getAssignedTaskVO(acceptedTaskId, acceptedByUserId);

		boolean taskAccepted=false;
		if(assignedTaskVO!=null){
			assignedTaskVO.setTaskStatus(1);
			taskAccepted=taskDao.acceptTask(assignedTaskVO);
			Logger.info("Sending notification to task owner.");
			
			UserDao userDao=new UserDaoImpl_MyBatis();
			User taskAssignorUser=userDao.getUserById(assignedTaskVO.getAssignorId());
			User taskAssigneeUser=userDao.getUserById(assignedTaskVO.getAssigneeId());
			
			if(taskAssignorUser!=null){
				String message=taskAssigneeUser.getName()+" is intrested in your task \""+Util.shortenTaskDesc(task.getTask_desc()+"..\"");
				Util.sendLikeNotifiction(taskAssigneeUser, taskAssignorUser, task);
			}

		}
		
		return taskAccepted;
	} 
	
	public boolean likeTask(int userIdWhoLikedTaskId, int likedTaskId) {
		Logger.info("TaskBO::likeTask()");
		if (userIdWhoLikedTaskId > 0 && likedTaskId > 0) {
			TaskDao taskDao = new TaskDaoImpl_MyBatis();

			UserDao userDao = new UserDaoImpl_MyBatis();
			Task task = taskDao.getTaskById(likedTaskId);
			User userWhoCreatedTask = userDao.getUserById(task.getUser_id());
			User userWhoLikedTask = userDao.getUserById(userIdWhoLikedTaskId);
			if (userWhoCreatedTask != null && userWhoLikedTask!=null && task!=null) {
				LikedTask likedTask=new LikedTask(likedTaskId, userWhoCreatedTask.getId(), userIdWhoLikedTaskId, null);
				taskDao.likeTask(likedTask);
				// this check is to stop sending notification to user himself/herself
				if(userWhoCreatedTask.getId()!=userIdWhoLikedTaskId){
					Util.sendLikeNotifiction(userWhoLikedTask, userWhoCreatedTask, task);
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean unLikeTask(int userIdWhoUnLikedTaskId, int unLikedTaskId) {
		Logger.info("TaskBO::unLikeTask()");
		if (userIdWhoUnLikedTaskId > 0 && unLikedTaskId > 0) {
			TaskDao taskDao = new TaskDaoImpl_MyBatis();
			taskDao.unLikeTask(userIdWhoUnLikedTaskId, unLikedTaskId);
			return true;

//			UserDao userDao = new UserDaoImpl_MyBatis();
//			Task task = taskDao.getTaskById(likedTaskId);
//			User userWhoCreatedTask = userDao.getUserById(task.getUser_id());
//			User userWhoLikedTask = userDao.getUserById(userIdWhoUnLikedTaskId);
//			if (userWhoCreatedTask != null && userWhoLikedTask!=null && task!=null) {
//				LikedTask likedTask=new LikedTask(likedTaskId, userWhoCreatedTask.getId(), userIdWhoLikedTaskId, null);
//				taskDao.likeTask(likedTask);
//				// this check is to stop sending notification to user himself/herself
//				if(userWhoCreatedTask.getId()!=userIdWhoUnLikedTaskId){
//					String message = "Your task has been accepted by " + userWhoLikedTask.getName();
//					List<User> userList = new ArrayList<User>();
//					userList.add(userWhoCreatedTask);
//					Util.notifyUsers(userList, message);
//				}
//				return true;
//			}
		}
		return false;
	}
	public List<Task> tasksByCategories(String categories){
		Logger.info("Task.taskAcceptRequest by user "+categories);
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.tasksByCategories(categories);
	}
	
	public void assignTask(Task taskVO,List<User> users){
		Logger.info("Task.assignTask().");
		if (taskVO != null && users.size() > 0) {
			TaskDao taskDao=new TaskDaoImpl_MyBatis();
			taskDao.assignTask(taskVO, users);
		}
	}



}
