package com.aztask.business;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import play.Logger.ALogger;

import com.aztask.data.TaskDao;
import com.aztask.data.mybatis.TaskDaoImpl_MyBatis;
import com.aztask.vo.AssignedTask;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public class TaskBO {

	public static ALogger Logger=play.Logger.of(TaskBO.class);
	
	public int createTask(Task task) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		task.setTask_time(sdf.format(new Date(System.currentTimeMillis())));
		int taskId = taskDao.createTask(task);
		return taskId;
	}

	public boolean deleteTask(int userId,int taskId){
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.deleteTask(userId,taskId);
	}
	public List<Task> newTasks() {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		try {
			return taskDao.newTasks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public List<Task> featuredTasks() {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.featuredTasks();

	}

	public List<Task> nearByTasks(String latitude, String longitude) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.nearByTasks(latitude,longitude);
	}

	public Task getTaskById(int taskId) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.getTaskById(taskId);
	}

	public List<Task> allTasksOfUser(int userId) {
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
		Logger.info("Task.taskAcceptRequest by user "+acceptedByUserId);
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		AssignedTask assignedTaskVO=taskDao.getAssignedTaskVO(acceptedTaskId, acceptedByUserId);
		Logger.info("Task.taskAcceptRequest by user "+assignedTaskVO);
		
		boolean taskAccepted=false;
		if(assignedTaskVO!=null){
			assignedTaskVO.setTaskStatus(1);
			taskAccepted=taskDao.acceptTask(assignedTaskVO);
		}
		
		return taskAccepted;
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
