package com.aztask.business;

import java.util.List;
import com.aztask.data.TaskDao;
import com.aztask.vo.TaskVO;

public class Task {

	public int createTask(TaskVO task){
		TaskDao taskDao = new TaskDao();
		int taskId=taskDao.createTask(task);
		return taskId;
	}

	public List<TaskVO> newTasks() throws Exception {
		TaskDao taskDao = new TaskDao();
		return taskDao.newTasks();
	}
	
	public TaskVO getTaskById(int taskId){
		TaskDao taskDao=new TaskDao();
		return taskDao.getTaskById(taskId);
	}

}
