package com.aztask.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="t_user")
public class UserVO implements Serializable{

	private static final long serialVersionUID = 8024117153604192506L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	private String contact;
	private String email;
	private String skills;
	
	@Column(name="device_id")
	private String deviceId;

	public UserVO() {

	}

	public UserVO(String name, String contact, String email, String skills) {
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.skills = skills;
	}

	public UserVO(int id, String name, String contact, String email,
			String skills) {
		this(name, contact, email, skills);
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
