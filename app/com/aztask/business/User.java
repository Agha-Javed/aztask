package com.aztask.business;

import java.sql.SQLException;
import java.util.List;

import com.aztask.data.UserDao;
import com.aztask.vo.NearbyUser;
import com.aztask.vo.Reply;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

public class User {

	/**
	 * @param user
	 * @return Reply
	 * 
	 * This method will do all business logic validation and will register user.
	 * @throws SQLException 
	 */
	public Reply registerUser(UserVO user) throws SQLException{
		UserDao userDao=new UserDao();
		return (userDao.registerUser(user)) ? new Reply("200","User Registerd.") :new Reply("401","Error in Registeration.");
	}
	
	public Reply isUserRegistered(int userId,String userDeviceId) throws Exception{
		UserDao userDao=new UserDao();
		return (userDao.isUserRegistered(userId,userDeviceId)) ? new Reply("200","true") :new Reply("401","false");
	}
	
	public List<TaskVO> tasksByUserId(int userId){
		return new UserDao().tasksByUserId(userId);
	}
	
	public UserVO userByTaskId(int taskId){
		
		TaskVO taskVO=new Task().getTaskById(taskId);
		if(taskVO!=null){
			return new UserDao().getUserById(taskVO.getUser_id());
		}
		return null;
	}
	
	public List<NearbyUser> getNearbyUsers(TaskVO task){
		return new UserDao().getNearbyUsers(task);
	}
}
