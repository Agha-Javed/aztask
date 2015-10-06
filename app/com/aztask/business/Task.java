package com.aztask.business;

import java.util.List;
import com.aztask.data.TaskDao;
import com.aztask.data.mybatis.TaskDaoImpl_MyBatis;
import com.aztask.vo.TaskVO;

public class Task {

	public int createTask(TaskVO task){
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		int taskId=taskDao.createTask(task);
		return taskId;
	}

	public List<TaskVO> newTasks() throws Exception {
		TaskDao taskDao = new TaskDaoImpl_MyBatis();
		return taskDao.newTasks();
	}
	
	public TaskVO getTaskById(int taskId){
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.getTaskById(taskId);
	}

	public List<TaskVO> getTasksByUserId(int userId){
		TaskDao taskDao=new TaskDaoImpl_MyBatis();
		return taskDao.tasksByUserId(userId);
	}
	
}
