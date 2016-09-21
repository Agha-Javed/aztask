package com.aztask.akka.actors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;

import com.aztask.business.TaskBO;
import com.aztask.service.UserService;
import com.aztask.util.Util;
import com.aztask.vo.Task;
import com.aztask.vo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger.ALogger;
import akka.actor.UntypedActor;

public class TaskNotifier extends UntypedActor {

	public final static ALogger log=play.Logger.of(TaskNotifier.class);
	
	public TaskNotifier() {
	}// end constructor
	
	@Override
	public void onReceive(final Object obj){
			Task taskVO =(Task)obj;
			log.info("Task VO:" + taskVO);
			List<User> nearbyUsers = getNearbyUsers(taskVO);
			log.info("Nearby Users:"+nearbyUsers);
			if(nearbyUsers!=null && nearbyUsers.size()>0){
				new TaskBO().assignTask(taskVO, nearbyUsers);
				notifyNearByUsers(Util.GCM_SERVER_API_KEY,nearbyUsers);
			}
	}

	public List<User> getNearbyUsers(Task taskVO){
		if(taskVO!=null){
			return UserService.getInstance().nearByUsers(taskVO);
			
		}
		return Collections.emptyList();
	}
	
	public static void notifyNearByUsers(String apiKey, List<User> nearbyUsers) {

		try {
			URL url = new URL("https://android.googleapis.com/gcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);
			conn.setDoOutput(true);

			ObjectMapper mapper = new ObjectMapper();
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

			AssignedTask content = new AssignedTask();
			String registeredKeys=getRegisteredKeys(nearbyUsers);
			log.info("Registered Keys:"+registeredKeys);
			content.addRegId(registeredKeys);
			content.createData("Task", "New task has been assigned to you.");

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
			return keys.substring(0, keys.length()-1);
		}
		return "";
	}

}// end class
