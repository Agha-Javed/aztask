package com.aztask.util;

import java.util.ArrayList;
import java.util.List;

import play.Logger.ALogger;

import com.aztask.vo.DeviceInfo;
import com.aztask.vo.User;

public class Util {
	
	public static String GCM_SERVER_API_KEY="AIzaSyA-RQ2LyoymIUOX32Dt12VvYeG0Iex3wnw";

	static ALogger logger=play.Logger.of(Util.class);

	public static String getWhereCluase(List<DeviceInfo> nearByUsers){
		logger.info("Util.getWhereCluase:: "+nearByUsers);
		StringBuffer whereCluase=new StringBuffer("'dummy_device_id',");
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
	
	
	public static String getRegisteredKeys(List<User> nearbyUsers){
		if(nearbyUsers!=null && nearbyUsers.size()>0){
			StringBuilder registeredKeys=new StringBuilder();
			for (User user : nearbyUsers) {
				
				registeredKeys.append(user.getGcm_token());
				registeredKeys.append(",");
			}
			
			String keys=registeredKeys.toString();
			return keys.substring(0, keys.length()-1);
		}
		
		return "";
	}



	public static void main(String[] args) {
		User user=new User();
		user.setGcm_token("asdvafdasfa");;

//		User user1=new User();
//		user1.setGcm_token("123131313");
		
		List<User> users=new ArrayList<User>();
	//	users.add(user1);
		users.add(user);
		
		String keys=getRegisteredKeys(users);
		
		System.out.println(keys);
		
	}
}
