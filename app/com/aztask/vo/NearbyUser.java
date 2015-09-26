package com.aztask.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_loc")
public class NearbyUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String device_id;

	private float latitude;
	private float longitude;
	
	public NearbyUser(){}
	
	public NearbyUser(String device_id, float latitude, float longitude) {
		super();
		this.device_id = device_id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
}
