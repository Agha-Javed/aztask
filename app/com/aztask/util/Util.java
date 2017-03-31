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

import com.aztask.akka.actors.NotificationData;
import com.aztask.akka.actors.TaskNotifier;
import com.aztask.vo.DeviceInfo;
import com.aztask.vo.Task;
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
		for(String taskCategory : categories.split(",")){
			likeCluase.append(" lower("+columnName+") like '%"+taskCategory.toLowerCase()+"%' or");
		}
		likeCluase.delete(likeCluase.length()-2, likeCluase.length());
		return likeCluase.toString();
		
	}
	
	public static void sendLikeNotifiction(User userWhoLikedTask,User userWhoCreatedTask,Task task){
		
		String message= userWhoLikedTask.getName()+" is intrested in your task \""+Util.shortenTaskDesc(task.getTask_desc()+"..\"");

		NotificationData notificationData=new NotificationData();
		
		notificationData.addRegId(userWhoCreatedTask.getGcm_token());
		notificationData.putData("title", "Task");
		notificationData.putData("action", "liked");
		notificationData.putData("message", message);
		notificationData.putData("task", ""+task.getTask_id());
		notificationData.putData("user", ""+userWhoLikedTask.getId());
		

		sendNotification(notificationData);

	}

	public static void sendAssignedNotifiction(List<User> usersList,Task task){
		
		
		NotificationData content = new NotificationData();
		String registeredKeys=getRegisteredKeys(usersList);
		log.info("Registered Keys:"+registeredKeys);
		content.addRegId(registeredKeys);

		String message="You might like \""+Util.shortenTaskDesc(task.getTask_desc()+"..\" task.");
		content.putData("title", "Task");
		content.putData("action", "assigned");
		content.putData("message", message);
		content.putData("task", ""+task.getTask_id());

		sendNotification(content);
		
	}

	public static void sendNotification(NotificationData notificationData) {

		try {
			URL url = new URL("https://android.googleapis.com/gcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + GCM_SERVER_API_KEY);
			conn.setDoOutput(true);

			ObjectMapper mapper = new ObjectMapper();
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

			mapper.writeValue(conn.getOutputStream(), notificationData);
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


	public static void insertEmptyLines(){
	  System.out.println("\n\n\n");
	};



	public static void insertEmptyLine(){
	  System.out.println("\n");
	};

	
	public static String shortenTaskDesc(String taskDesc){
		if(taskDesc!=null && taskDesc.length()>0){
			taskDesc = taskDesc.substring(0, Math.min(taskDesc.length(), 15));
			return taskDesc;
		}
		
		return "";
	}
	

	public static void main(String[] args) {
//		String s="I nee";
//		System.out.println("Before:"+s);
//		s = s.substring(0, Math.min(s.length(), 10));
//		System.out.println("After:"+s);
		
		//testLikeNotification();
		testAssignedTaskNotification();
	}
	
	public static void testAssignedTaskNotification(){
		
		User userToBeAssigned=new User();
		userToBeAssigned.setGcm_token("cYq3vHTolFI:APA91bE-pQYmKlKoTAvOu4xYSuNYfjrwayR8Jd8DrVp016T5KCCuocOsxs8Trq5-serovhjM1sl-LX8N3wt3nkeOXVQYSsaUoa8uuALvOoI_7ys5s_ahoSVyy1snGmtqEI5E-g9ma8ZY");
		userToBeAssigned.setName("Javed");

		Task assignedTask=new Task();
		assignedTask.setTask_id(54);
		assignedTask.setTask_desc("I need photographer.");
		//user.setGcm_token("fuljMwMLaQw:APA91bG_uWuw6wo2wvuy41SzWwR5ea2Kz2DD-uDG2wIzofSA7O7yB_5C9p_8jYq8VSr2o-_kEwGlLfc8sgvAQVriN20MTo_wymKwDBFis7xAcQl694-xUCbGp32zvAiwulUioZnKy6u1");

		
//		User user1=new User();
//		user1.setGcm_token("123131313");
		
		List<User> users=new ArrayList<User>();
		users.add(userToBeAssigned);

		sendAssignedNotifiction(users, assignedTask);
		
		
		
	}
	public static void testLikeNotification(){

		User userWhoCreateTask=new User();
		userWhoCreateTask.setGcm_token("dvU6jJBQpFQ:APA91bFvwRAQnffiuCWDUzRKE-LgW8dtekAZBy94I0qVc_p94ZiT-FvMHoJUAAcoEcoB6I3QkiYUp1a28s2NcbLWe6-XrTGOqgHVS7T1BAUx93N6RgNvsoYEWQ86mU57KegN8dwKZMS_");
		userWhoCreateTask.setName("Javed");

		
		User userWhoLikedTask=new User();
		userWhoLikedTask.setName("Zuhaib");
		userWhoLikedTask.setId(8);

		
		Task likedTask=new Task();
		likedTask.setTask_id(54);
		likedTask.setTask_desc("I need photographer.");
		//user.setGcm_token("fuljMwMLaQw:APA91bG_uWuw6wo2wvuy41SzWwR5ea2Kz2DD-uDG2wIzofSA7O7yB_5C9p_8jYq8VSr2o-_kEwGlLfc8sgvAQVriN20MTo_wymKwDBFis7xAcQl694-xUCbGp32zvAiwulUioZnKy6u1");

		
//		User user1=new User();
//		user1.setGcm_token("123131313");
		
		List<User> users=new ArrayList<User>();
	//	users.add(user1);
		users.add(userWhoCreateTask);
		
		//String keys=getRegisteredKeys(users);
		String message="you might like \""+Util.shortenTaskDesc("I need photographer")+"...\" task.";
	//	notifyUsers(users,"assigned", message, 56);
		//notifyUsers(users,"liked", "Task has been liked.", 54);
		sendLikeNotifiction(userWhoLikedTask,userWhoCreateTask,likedTask);

		
		//System.out.println(keys);
		

		
	}
}
