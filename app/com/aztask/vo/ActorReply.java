package com.aztask.vo;

/**
 * @author Javed
 * 
 * This class will hold information, which could be used by other actors to do subsquent tasks.
 *
 */
public class ActorReply {
	
	private boolean isSuccess;
	private int taskId;
	
	public ActorReply(boolean isSuccess, int taskId) {
		super();
		this.isSuccess = isSuccess;
		this.taskId = taskId;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean status) {
		this.isSuccess = status;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}
