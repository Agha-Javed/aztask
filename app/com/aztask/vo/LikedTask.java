package com.aztask.vo;

public class LikedTask {

	private int id;
	
	private int taskId;
	
	private int taskOwnerId;
	
	private int userWhoLikedTask;
	
	private String comments;
	
	public LikedTask() {
		// TODO Auto-generated constructor stub
	}

	public LikedTask(int taskId, int taskOwnerId, int userWhoLikedTask, String comments) {
		super();
		this.taskId = taskId;
		this.taskOwnerId = taskOwnerId;
		this.userWhoLikedTask = userWhoLikedTask;
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getTaskOwnerId() {
		return taskOwnerId;
	}

	public void setTaskOwnerId(int taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}

	public int getUserWhoLikedTask() {
		return userWhoLikedTask;
	}

	public void setUserWhoLikedTask(int userWhoLikedTask) {
		this.userWhoLikedTask = userWhoLikedTask;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	

	
}
