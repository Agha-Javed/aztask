package com.aztask.data.mybatis;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.DeviceDao;
import com.aztask.vo.DeviceInfo;

public class DeviceDaoImpl_MyBatis implements DeviceDao{

	ALogger logger=play.Logger.of(this.getClass());

	public DeviceDaoImpl_MyBatis() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean updateDeviceLocation(DeviceInfo deviceInfo) {
		try{
			SqlSession session=MyBatis_SessionFactory.openSession();
			logger.info("UserDaoImpl_MyBatis - > updateDeviceLocation "+deviceInfo);
			int result=session.update("Device.updateLocation", deviceInfo);
			logger.info("UserDaoImpl_MyBatis - > Location updated result  "+result);
			session.commit();
			session.close();
			return (result>0) ? true : false;
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return false;
	}

}
