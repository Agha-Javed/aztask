package com.aztask.business;

import java.util.List;
import com.aztask.data.TaskDao;
import com.aztask.vo.Reply;
import com.aztask.vo.TaskVO;

public class Task {

	public Reply createTask(TaskVO task) throws Exception {
		TaskDao taskDao = new TaskDao();
		return (taskDao.createTask(task)) ? new Reply("200", "Task saved.")
				: new Reply("401", "Error in saving task.");
	}

	public List<TaskVO> newTasks() throws Exception {
		TaskDao taskDao = new TaskDao();
		return taskDao.newTasks();
	}

}
