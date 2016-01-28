package com.aztask.vo;

public class AssignedTask {

	private int id;
	private int assignorId;
	private int assigneeId;
	private int taskId;
	private int taskStatus;
	
	
	public AssignedTask(){}

	public AssignedTask(int id, int assignorId, int assigneeId, int taskId,int taskStatus) {
		super();
		this.id = id;
		this.assignorId = assignorId;
		this.assigneeId = assigneeId;
		this.taskId = taskId;
		this.taskStatus = taskStatus;
	}

	public AssignedTask(int assignorId, int assigneeId, int taskId,int taskStatus) {
		super();
		this.assignorId = assignorId;
		this.assigneeId = assigneeId;
		this.taskId = taskId;
		this.taskStatus = taskStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssignorId() {
		return assignorId;
	}

	public void setAssignorId(int assignorId) {
		this.assignorId = assignorId;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public String toString() {
		return "AssignedTaskVO [id=" + id + ", assignorId=" + assignorId
				+ ", assigneeId=" + assigneeId + ", taskId=" + taskId
				+ ", taskStatus=" + taskStatus + "]";
	}
	
	

}
