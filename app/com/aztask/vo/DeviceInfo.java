package com.aztask.vo;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String deviceId;
	private String latitude;
	private String longitude;
	
	public DeviceInfo(){}
	
	public DeviceInfo(String device_id, String latitude, String longitude) {
		super();
		this.deviceId = device_id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String device_id) {
		this.deviceId = device_id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Device Info [device_id=" + deviceId + "+ , latitude="+latitude+" ,longitude="+longitude+"]";
	}
	
}
