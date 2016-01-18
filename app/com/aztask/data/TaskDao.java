package com.aztask.data;

import java.util.List;

import com.aztask.vo.NearByDevice;
import com.aztask.vo.TaskVO;

public interface TaskDao {
	public int createTask(TaskVO task);

	public List<TaskVO> newTasks();
	public List<TaskVO> featuredTasks();
	public List<TaskVO> nearByTasks(NearByDevice nearBy);

	
	public List<TaskVO> getTasksByUser(int userId);
	public List<TaskVO> pendingTasksOfUser(int userId);
	public List<TaskVO> acceptedTasksOfUser(int userId);

	
	public TaskVO getTaskById(int taskId);

}
