package com.aztask.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



public class User implements Serializable{

	private static final long serialVersionUID = 8024117153604192506L;

	@JsonIgnoreProperties
	private int id;
	
	private String name;
	private String contact;
	private String email;
	private String skills;
	private String deviceId;

	public User() {

	}

	public User(String name, String contact, String email, String skills,String device_id) {
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.skills = skills;
		this.deviceId=device_id;
	}

	public User(int id, String name, String contact, String email,
			String skills,String device_id) {
		this(name, contact, email, skills,device_id);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	@Override
	public String toString() {
		return "UserVO [name=" + name + "]";
	}

}
