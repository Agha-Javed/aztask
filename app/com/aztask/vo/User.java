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
	
	private DeviceInfo deviceInfo;

	public User() {

	}

	public User(String name, String contact, String email, String skills,DeviceInfo deviceInfo) {
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.skills = skills;
		this.deviceInfo=deviceInfo;
	}

	public User(int id, String name, String contact, String email,
			String skills,DeviceInfo deviceInfo) {
		this(name, contact, email, skills,deviceInfo);
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


	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	
	@Override
	public String toString() {
		return "UserVO [name=" + name + "]";
	}

}
