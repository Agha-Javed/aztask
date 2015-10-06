package com.aztask.vo;

import java.io.Serializable;



public class UserVO implements Serializable{

	private static final long serialVersionUID = 8024117153604192506L;

	private int id;
	
	private String name;
	private String contact;
	private String email;
	private String skills;
	private String device_id;

	public UserVO() {

	}

	public UserVO(String name, String contact, String email, String skills,String device_id) {
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.skills = skills;
		this.device_id=device_id;
	}

	public UserVO(int id, String name, String contact, String email,
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

	public String getDevice_Id() {
		return device_id;
	}

	public void setDevice_Id(String deviceId) {
		this.device_id = deviceId;
	}


	@Override
	public String toString() {
		return "UserVO [name=" + name + "]";
	}

}
