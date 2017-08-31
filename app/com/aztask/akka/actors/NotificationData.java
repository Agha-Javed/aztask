package com.aztask.akka.actors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NotificationData implements Serializable {

	private static final long serialVersionUID = 1L;
	public List<String> registration_ids;
	public Map<String,String> data;

	public NotificationData(){
		data = new HashMap<String,String>();
	}
    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }

    public void createTaskNotificationData(String title,String action, String message,String taskId){
        if(data == null)
            data = new HashMap<String,String>();

        data.put("title", title);
        data.put("action", action);
        data.put("message", message);
        data.put("task", taskId);
        
        
        
    }
    
    public void putData(String key, String value){
    	data.put(key,value);
    }
}