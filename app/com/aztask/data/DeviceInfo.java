package com.aztask.data;

/**
 * @author Javed
 * 
 * This class represents, the mobile phone device information i.e. Its location, device id.
 *
 */
public class DeviceInfo {
	
	private String latitude;
	private String longtitude;
	private String deviceId;

	private DeviceInfo(){}
	public DeviceInfo(String latitude, String longtitude, String deviceId) {
		this();
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.deviceId = deviceId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	

	

}
