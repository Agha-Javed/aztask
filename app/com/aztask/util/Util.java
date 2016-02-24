package com.aztask.util;

import java.util.List;

import play.Logger.ALogger;

import com.aztask.vo.DeviceInfo;

public class Util {
	

	static ALogger logger=play.Logger.of(Util.class);

	public static String getWhereCluase(List<DeviceInfo> nearByUsers){
		logger.info("Util.getWhereCluase:: "+nearByUsers);
		StringBuffer whereCluase=new StringBuffer("");
		for(DeviceInfo nearByUser: nearByUsers){
			logger.info("Nearby User :: "+nearByUser);
			whereCluase.append("'"+nearByUser.getDeviceId()+"',");
		}
		if(whereCluase.length()>0)
		whereCluase.deleteCharAt(whereCluase.length()-1);
		
		return whereCluase.toString();
	}
	
	public static String getLikeClause(String columnName,String categories){
		
		StringBuffer likeCluase=new StringBuffer();
		for(String taskCategory : categories.split(";")){
			likeCluase.append(" lower("+columnName+") like '%"+taskCategory.toLowerCase()+"%' or");
		}
		likeCluase.delete(likeCluase.length()-2, likeCluase.length());
		return likeCluase.toString();
		
	}


}
