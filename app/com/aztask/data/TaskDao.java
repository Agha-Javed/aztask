package com.aztask.data;

import java.util.List;

import com.aztask.vo.AssignedTask;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public interface TaskDao {
	public int createTask(Task task);
	public boolean deleteTask(int userId,int taskId);
	public List<Task> newTasks();
	public List<Task> featuredTasks();
	public List<Task> nearByTasks(String latitude, String longitude);
	public List<Task> getTasksByUser(int userId);
	public List<Task> pendingTasksOfUser(int userId);
	public List<Task> acceptedTasksOfUser(int userId);
	public List<Task> tasksByCategories(String categories);
	public Task getTaskById(int taskId);
	public void assignTask(Task taskVO, List<User>users);
	public AssignedTask getAssignedTaskVO(int taskId,int assigneeId);
	public List<Integer> getTasksByAssignee(int assigneeId);
	public boolean acceptTask(AssignedTask assignedTaskVO);

}
