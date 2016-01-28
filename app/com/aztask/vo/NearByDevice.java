package com.aztask.vo;

import java.io.Serializable;

public class NearByDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	private String device_id;
	private float latitude;
	private float longitude;
	
	public NearByDevice(){}
	
	public NearByDevice(String device_id, float latitude, float longitude) {
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

	@Override
	public String toString() {
		return "NearbyUser [device_id=" + device_id + "]";
	}
	
}
