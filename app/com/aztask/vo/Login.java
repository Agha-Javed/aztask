package com.aztask.vo;

public class Login {

	private String email;
	private String phoneNumber;
	private String password;
	private String deviceId;
	
	public Login() {}

	public Login(String email, String phoneNumber, String password,String deviceId) {
		super();
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.deviceId = deviceId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
