package com.aztask.data;

import java.util.List;
import com.aztask.vo.TaskVO;

public interface TaskDao {
	public int createTask(TaskVO task);

	public List<TaskVO> newTasks()throws Exception;
	
	public TaskVO getTaskById(int taskId);
	
	public List<TaskVO> tasksByUserId(int userId);
}
