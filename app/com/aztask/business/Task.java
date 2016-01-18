package com.aztask.business;

import java.util.Collections;
import java.util.List;

import com.aztask.data.TaskDao;
import com.aztask.data.mybatis.TaskDaoImpl_MyBatis;
import com.aztask.vo.NearByDevice;
import com.aztask.vo.TaskVO;

public class Task {

	public int createTask(TaskVO task) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		int taskId = taskDao.createTask(task);
		return taskId;
	}

	public List<TaskVO> newTasks() {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		try {
			return taskDao.newTasks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public List<TaskVO> featuredTasks() {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.featuredTasks();

	}

	public List<TaskVO> nearByTasks(NearByDevice nearByLocation) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.nearByTasks(nearByLocation);
	}

	public TaskVO getTaskById(int taskId) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.getTaskById(taskId);
	}

	public List<TaskVO> allTasksOfUser(int userId) {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.getTasksByUser(userId);
	}

	public List<TaskVO> pendingTasksOfUser(int userId) {
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.pendingTasksOfUser(userId);
	}
	
	public List<TaskVO> acceptedTasksOfUser(int userId){
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.acceptedTasksOfUser(userId);
	}
	
}
