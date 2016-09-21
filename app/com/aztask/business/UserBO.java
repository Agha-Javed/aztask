package com.aztask.business;

import java.sql.SQLException;
import java.util.List;
import play.Logger.ALogger;
import play.libs.Json;

import com.aztask.data.DeviceDao;
import com.aztask.data.UserDao;
import com.aztask.data.mybatis.DeviceDaoImpl_MyBatis;
import com.aztask.data.mybatis.UserDaoImpl_MyBatis;
import com.aztask.vo.DeviceInfo;
import com.aztask.vo.Login;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public class UserBO {

	public static ALogger logger = play.Logger.of(UserBO.class);
	/**
	 * @param user
	 * @return Reply
	 * 
	 * This method will do all business logic validation and will register user.
	 * @throws SQLException 
	 */
	public String registerUser(User user){
		
		Reply reply=validateData(user);
		if(!reply.getCode().equals("200")){
			logger.info(reply.getMessage());
			return "{\"code\":\"400\",\"id\":\"0\"}";
		}
		UserDao userDao=new UserDaoImpl_MyBatis();
		User registeredUser=userDao.registerUser(user);
		return (registeredUser!=null) ?  Json.stringify(Json.toJson(registeredUser)) : "{\"code\":\"400\",\"id\":\"0\"}" ;
	}
	
	public Reply updateUserProfile(User user){
//		if(user==null || user.getDeviceId()==null){
//			return new Reply("400","Invalid User Id.");
//		}
//
		UserDao userDao=new UserDaoImpl_MyBatis();
//		if(!userDao.isUserRegistered(user.getDeviceId())){
//			return new Reply("400","User doesn't exist.");
//		}
		
		return (userDao.updateUserProfile(user)) ? new Reply("200","User Profile Updated.") :new Reply("401","Error in updating profile.");
	}
	
	public Reply login(Login loginCredentials){
		
		if(loginCredentials==null || loginCredentials.getDeviceId()==null || loginCredentials.getDeviceId().trim().equals("")){
			return new Reply("400","Invalid User.");
		}
		
		if( (loginCredentials.getEmail()==null || loginCredentials.getEmail().equals("")) && (loginCredentials.getPhoneNumber()==null || loginCredentials.getPhoneNumber().equals(""))){
			return new Reply("400","Invalid Credentials provided.");
		}

		// TODO Other validation, like sql injection will go here
		
		UserDao userDao=new UserDaoImpl_MyBatis();
		return (userDao.login(loginCredentials)) ? new Reply("200","Login Successful.") :new Reply("401","Login Failed.");
	}
	
	public String isUserRegistered(String userDeviceId){
		UserDao userDao=new UserDaoImpl_MyBatis();
//		return (userDao.isUserRegistered(userDeviceId)) ? new Reply("200","true") :new Reply("401","false");
		User registeredUser=userDao.isUserRegistered(userDeviceId);
		if(registeredUser!=null && registeredUser.getId()>0){
			DeviceDao deviceDao=new DeviceDaoImpl_MyBatis();
			DeviceInfo deviceInfo=deviceDao.getDeviceInfoById(registeredUser.getDeviceId());
			registeredUser.setDeviceInfo(deviceInfo);
		}

		return (registeredUser!=null && registeredUser.getId()>0) ? Json.stringify(Json.toJson(registeredUser)) :"{\"code\":\"400\",\"id\":\"0\"}";

	}
	

	public User userByTaskId(int taskId){
		
		Task taskVO=new TaskBO().getTaskById(taskId);
		if(taskVO!=null){
			return new UserDaoImpl_MyBatis().getUserById(taskVO.getUser_id());
		}
		return null;
	}

	public User getUserById(int userId){
		
		if(userId>0){
			return new UserDaoImpl_MyBatis().getUserById(userId);
		}
		return null;
	}

	public List<User> nearByUsers(Task task){
		
	    return new UserDaoImpl_MyBatis().findNearByUsers(task);
	}
	
	private Reply validateData(User user){
		
		UserDao userDaoRef=new UserDaoImpl_MyBatis();
		if(userDaoRef.findUserByName(user.getName().trim().toLowerCase())>0){
			return new Reply("400","User with name [ "+user.getName()+" ] already exists.");
		}
		

		if(userDaoRef.findUserByEmail(user.getEmail().trim().toLowerCase())>0){
			return new Reply("400","User with email [ "+user.getEmail()+" ] already exists.");
		}

		if(userDaoRef.findUserByPhone(user.getContact().trim())>0){
			return new Reply("400","User with phone [ "+user.getContact()+" ] already exists.");
		}

		
		return new Reply("200","Data is valid.");
	}

	public Reply registerGCMToken(int userId, String gcmToken) {
		logger.info("UserBO - > registerGCMToken ");

		if(userId>0 && gcmToken!=null && gcmToken.length()>0){

			UserDao userDao=new UserDaoImpl_MyBatis();
			User user=userDao.getUserById(userId);
			if(user==null){
				return new Reply("400","User doesn't exists.");
			}
			
			logger.info("UserBO - > registring token");
			
			return (userDao.registerGCMToken(userId,gcmToken)) ? new Reply("200","Token Registered.") : new Reply("400","Token Registration Error.");
			
		}
		return new Reply("400","Token Registration Error.");
	}
	

}
