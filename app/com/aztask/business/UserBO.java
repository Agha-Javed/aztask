package com.aztask.business;

import java.sql.SQLException;
import java.util.List;

import com.aztask.data.UserDao;
import com.aztask.data.mybatis.UserDaoImpl_MyBatis;
import com.aztask.vo.Login;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public class UserBO {

	/**
	 * @param user
	 * @return Reply
	 * 
	 * This method will do all business logic validation and will register user.
	 * @throws SQLException 
	 */
	public Reply registerUser(User user){
		UserDao userDao=new UserDaoImpl_MyBatis();
		return (userDao.registerUser(user)) ? new Reply("200","User Registerd.") :new Reply("401","Error in Registeration.");
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
	
	public Reply isUserRegistered(String userDeviceId){
		UserDao userDao=new UserDaoImpl_MyBatis();
		return (userDao.isUserRegistered(userDeviceId)) ? new Reply("200","true") :new Reply("401","false");
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

}
