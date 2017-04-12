package com.aztask.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
		
		String message= userWhoLikedTask.getName()+" is intrested in your work \""+Util.shortenTaskDesc(task.getTask_desc())+"..\" you posted.";

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

		String message="You might like \""+Util.shortenTaskDesc(task.getTask_desc())+"..\" work.";
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
			taskDesc = taskDesc.substring(0, Math.min(taskDesc.length(), 30));
			return taskDesc;
		}
		
		return "";
	}
	

	public static void main(String[] args) throws Exception {
//		String s="I nee";
//		System.out.println("Before:"+s);
//		s = s.substring(0, Math.min(s.length(), 10));
//		System.out.println("After:"+s);
		
		//testLikeNotification();
		//testAssignedTaskNotification();
		
		//testTimeZone();
		testDate();
	}
	
	public static void testDate(){
		
//		String date="2017-04-03 09:48:16";
		String date1="2017-04-10 09:39:12";
		String date2="2017-04-03 09:48:16";
		
		List<String> dates=new ArrayList<>();
	    final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	    final SimpleDateFormat dateFormatter=new SimpleDateFormat(TIME_FORMAT);

		dates.add("2017-03-31 14:33:56");
		dates.add("2017-03-31 19:56:26");
		dates.add("2017-04-01 09:01:07");
		dates.add("2017-04-08 05:07:19");
		dates.add("2017-04-10 10:59:10");
		dates.add("2017-04-10 11:00:00");
		dates.add("2017-04-10 12:24:20");
		dates.add("2017-04-10 13:55:23");
		dates.add("2017-04-08 05:11:38");
		dates.add("2017-04-08 05:12:29");
		dates.add("2017-04-10 02:24:45");
		dates.add("2017-04-03 09:48:16");
		dates.add("2017-04-03 10:13:12");
		dates.add("2017-04-07 10:29:17");
		
		
		Collections.sort(dates, new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
		        try {
		            Date task1Date = dateFormatter.parse(o1);
		            Date task2Date = dateFormatter.parse(o2);

		            System.out.println("Date 1:"+task1Date+" and Date 2:"+task2Date+" and compare value:"+task1Date.compareTo(task2Date) );

		            return task1Date.compareTo(task2Date);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        return 0;
			}
			
			
		});

		Collections.reverse(dates);;

		System.out.println("Sorting completed.");
		for (String string : dates) {
			System.out.println(string);
		}

		
/*        try {
            Date task1Date = dateFormatter.parse(date1);
            Date task2Date = dateFormatter.parse(date2);

            if (task1Date == null || task2Date == null)
                System.out.println("Its Null");
            
            System.out.println(task1Date.compareTo(task2Date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
*/

	}
	
	public static void testTimeZone(){
		
		TimeZone kHITimeZone = TimeZone.getTimeZone("Asia/Karachi");
		TimeZone SGTimeZone = TimeZone.getTimeZone("Asia/Singapore");
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(kHITimeZone);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(kHITimeZone);

		System.out.println(sdf.format(new Date(calendar.getTimeInMillis())));
		
		calendar.setTimeZone(SGTimeZone);
		sdf.setTimeZone(SGTimeZone);
		System.out.println(sdf.format(new Date(calendar.getTimeInMillis())));

/*		Date date=new Date();
		
		date.to
		
		Calendar karachi=Calendar.getInstance(KLTimeZone);
		System.out.println("UTC:"+new Date(date.getTime()));
		System.out.println(sdf.format(new Date(karachi.getTimeInMillis())));
*/
	}
	
	public static void testAssignedTaskNotification(){
		
		User userToBeAssigned=new User();
//		userToBeAssigned.setGcm_token("eikDcE-sAQs:APA91bFcCLJh8n217hO6ZauPUN9MkJmBhchOpp--Pqe6656u2R4iGNAZakJmIgycwQUtoJeBh0wqClJtDUtdtfHCWlXtFF5Lkzy9SVl8m-k2bI5AFSkNCg1TuOGSNgijsHT2dK5mjRnV");
		userToBeAssigned.setGcm_token("cPQx_2uVgO4:APA91bGWmJNIb9AySYtkieANiTsqaiANQ3KAgL0b28ZgCvoNRoraM5yo09529l5F6R7noML3WB5brTq_GZj6WXlbcOIMzNQs_dVmyo4pYqfvKLzUICw8DXq-GQX2GYxK0ssjQBYQd7iF");

		
		userToBeAssigned.setName("Javed");

		Task assignedTask=new Task();
		assignedTask.setTask_id(8);
		assignedTask.setTask_desc("need someone to get me food.");
		//user.setGcm_token("fuljMwMLaQw:APA91bG_uWuw6wo2wvuy41SzWwR5ea2Kz2DD-uDG2wIzofSA7O7yB_5C9p_8jYq8VSr2o-_kEwGlLfc8sgvAQVriN20MTo_wymKwDBFis7xAcQl694-xUCbGp32zvAiwulUioZnKy6u1");

		
//		User user1=new User();
//		user1.setGcm_token("123131313");
		
		List<User> users=new ArrayList<User>();
		users.add(userToBeAssigned);

		sendAssignedNotifiction(users, assignedTask);
		
		
		
	}
	public static void testLikeNotification(){

		User userWhoCreateTask=new User();
		userWhoCreateTask.setGcm_token("cPQx_2uVgO4:APA91bGWmJNIb9AySYtkieANiTsqaiANQ3KAgL0b28ZgCvoNRoraM5yo09529l5F6R7noML3WB5brTq_GZj6WXlbcOIMzNQs_dVmyo4pYqfvKLzUICw8DXq-GQX2GYxK0ssjQBYQd7iF");
		userWhoCreateTask.setName("Bilal");

		
		User userWhoLikedTask=new User();
		userWhoLikedTask.setName("Javed");
		userWhoLikedTask.setId(1);

		
		Task likedTask=new Task();
		likedTask.setTask_id(1);
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

		
		
		//public static void sendAssignedNotifiction(List<User> usersList,Task task){
		
		
		//System.out.println(keys);
		
	   

		
	}
}
