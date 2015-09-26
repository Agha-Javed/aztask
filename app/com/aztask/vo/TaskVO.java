package com.aztask.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_task")
public class TaskVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int task_id;

	private String task_desc;
	private String task_categories;
	private int user_id;
	
	private String latitude;
	private String longitude;
	private String device_id;


	public TaskVO() {

	}

	public TaskVO(String task_desc, String task_categories, int user_id) {
		super();
		this.task_desc = task_desc;
		this.task_categories = task_categories;
		this.user_id = user_id;
	}

	
	public TaskVO(String task_desc, String task_categories, int user_id,String latitude, String longitude, String deviceId) {
		super();
		this.task_desc = task_desc;
		this.task_categories = task_categories;
		this.user_id = user_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.device_id = deviceId;
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
	
	@Override
	public String toString() {
		return "TaskVO [task_desc=" + task_desc + "]";
	}


}
