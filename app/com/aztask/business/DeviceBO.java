package com.aztask.business;

import com.aztask.data.DeviceDao;
import com.aztask.data.mybatis.DeviceDaoImpl_MyBatis;
import com.aztask.vo.DeviceInfo;

import play.Logger.ALogger;

public class DeviceBO {

	public static ALogger Logger=play.Logger.of(DeviceBO.class);

	public DeviceBO() {	}
	
	public boolean updateDeviceLocation(DeviceInfo deviceInfo){
		Logger.info("DeviceBO->updateDeviceLocation() entering");
		DeviceDao deviceInfoDao=new DeviceDaoImpl_MyBatis();
		return deviceInfoDao.updateDeviceLocation(deviceInfo);
	}

}
