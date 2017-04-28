package com.aztask.vo;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private int task_id;
	private int user_id;
	private String task_desc;
	private String latitude;
	private String longitude;
	private String device_id;
	private String task_time;
	private String task_categories;
	private String task_location;
	private String task_min_max_budget;
	
	
	private String liked="";
	private String user="";
	private String contact="";


	public Task() {

	}

	public Task(String task_desc, String task_categories, int user_id) {
		super();
		this.task_desc = task_desc;
		this.task_categories = task_categories;
		this.user_id = user_id;
	}

	
	public Task(String task_desc, String task_categories, int user_id,String latitude, String longitude, String deviceId,String task_time,String task_location,String task_min_max_budget,String liked) {
		super();
		this.task_desc = task_desc;
		this.task_categories = task_categories;
		this.user_id = user_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.device_id = deviceId;
		this.task_time=task_time;
		this.task_location=task_location;
		this.task_min_max_budget=task_min_max_budget;
		this.liked=liked;
		
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public String getTask_desc() {
		return task_desc;
	}

	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}

	public String getTask_categories() {
		return task_categories;
	}

	public void setTask_categories(String task_categories) {
		this.task_categories = task_categories;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongtitude() {
		return longitude;
	}
	public void setLongtitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String deviceId) {
		this.device_id = deviceId;
	}
	
	
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTask_time() {
		return task_time;
	}

	public void setTask_time(String task_time) {
		this.task_time = task_time;
	}

	
	public String getTask_min_max_budget() {
		return task_min_max_budget;
	}

	public void setTask_min_max_budget(String task_min_max_budget) {
		this.task_min_max_budget = task_min_max_budget;
	}
	
	public String getTask_location() {
		return task_location;
	}

	public void setTask_location(String task_location) {
		this.task_location = task_location;
	}


	public String getLiked() {
		return liked;
	}

	public void setLiked(String liked) {
		this.liked = liked;
	}

	@Override
	public String toString() {
		return "TaskVO [task_desc=" + task_desc + "]";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}


}
