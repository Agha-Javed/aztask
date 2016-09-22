package com.aztask.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import play.Logger.ALogger;

import com.aztask.akka.actors.AssignedTask;
import com.aztask.akka.actors.TaskNotifier;
import com.aztask.vo.DeviceInfo;
import com.aztask.vo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	public static String GCM_SERVER_API_KEY="AIzaSyA-RQ2LyoymIUOX32Dt12VvYeG0Iex3wnw";
	public final static ALogger log=play.Logger.of(TaskNotifier.class);


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
	
	public static void notifyUsers(List<User> nearbyUsers,String message) {

		try {
			URL url = new URL("https://android.googleapis.com/gcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + GCM_SERVER_API_KEY);
			conn.setDoOutput(true);

			ObjectMapper mapper = new ObjectMapper();
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

			AssignedTask content = new AssignedTask();
			String registeredKeys=getRegisteredKeys(nearbyUsers);
			log.info("Registered Keys:"+registeredKeys);
			content.addRegId(registeredKeys);
			content.createData("Task", message);

			mapper.writeValue(conn.getOutputStream(), content);
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// 7. Print result
			System.out.println(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	
	
	public static String getRegisteredKeys(List<User> nearbyUsers){
		if(nearbyUsers!=null && nearbyUsers.size()>0){
			StringBuilder registeredKeys=new StringBuilder();
			for (User user : nearbyUsers) {
				registeredKeys.append(user.getGcm_token());
				registeredKeys.append(",");
			}
			String keys=registeredKeys.toString();
			return keys.substring(0, keys.length()-1).trim();
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
