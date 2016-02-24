package com.aztask.service;

import com.aztask.business.DeviceBO;
import com.aztask.vo.DeviceInfo;

import play.Logger.ALogger;

public class DeviceService {

	private static DeviceService deviceService;
	
	public static ALogger device_service_log=play.Logger.of(DeviceService.class);

	
	private DeviceService() {}	
	
	public boolean updateDeviceLocation(DeviceInfo deviceInfo) {
		device_service_log.info("Updating device location.");
		return new DeviceBO().updateDeviceLocation(deviceInfo);
	}
	
	synchronized public static DeviceService getInstance(){
		device_service_log.info("Initializing DeviceService");
		if(deviceService==null){
			deviceService=new DeviceService();
		}
		return deviceService;
	}	

}
