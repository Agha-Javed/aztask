package com.aztask.vo;

/**
 * @author Javed
 * 
 * This class represents the data of accepted task, once any consumer accepts the task
 * this object will hold that information.
 *
 */
public class AcceptedTaskVO {
	
	private int userId;
	private int taskId;
	
	public AcceptedTaskVO(){}

	public AcceptedTaskVO(int userId, int taskId) {
		super();
		this.userId = userId;
		this.taskId = taskId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "AcceptedTaskVO [userId=" + userId + ", taskId=" + taskId + "]";
	}
	
	
	
}
