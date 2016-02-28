package com.aztask.data.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.UserDao;
import com.aztask.util.Util;
import com.aztask.vo.Login;
import com.aztask.vo.DeviceInfo;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public class UserDaoImpl_MyBatis implements UserDao{
	
	ALogger logger=play.Logger.of(this.getClass());
	
	public UserDaoImpl_MyBatis() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public List<User> findNearByUsers(Task task) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		List<DeviceInfo> nearByDevices=session.selectList("User.getNearbyDevices", task);//''selectList("Task.getTaskById", userId);
		logger.info("UserDaoImpl_MyBatis - > findNearByUsers:: number of nearby devices "+nearByDevices.size());
		String deviceIds=Util.getWhereCluase(nearByDevices);
		if(deviceIds!=null && deviceIds.length()>0 && deviceIds.indexOf(task.getDevice_id())>0){
			deviceIds=deviceIds.replace(task.getDevice_id(), "");
		}
		logger.info("UserDaoImpl_MyBatis - > findNearByUsers:: near by devices "+deviceIds);
		String skills=Util.getLikeClause("skills",task.getTask_categories());
		logger.info("UserDaoImpl_MyBatis - > findNearByUsers:: skills to find for "+skills);
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("deviceIds", deviceIds);
		params.put("skills", skills);

		List<User> relatedUsers=session.selectList("User.selectNearbyUsers",params);
		return relatedUsers;
		//return session.getMapper(NearbyUserWrapper.class).nearByUsers(deviceIds, skills);
	}
	
	@Override
	public User getUserById(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("UserDaoImpl_MyBatis - > getUserById::");
		User userVO=session.selectOne("User.getUserById", userId);
		logger.info("UserDaoImpl_MyBatis - > Found User ::"+userVO);
		return userVO;
	}
	
	@Override
	public boolean login(Login loginCredentials) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("UserDaoImpl_MyBatis - > login:: checking login");
		String query="";
		if(loginCredentials!=null && loginCredentials.getEmail()!=null){
			query="User.login_by_email";
		}else if(loginCredentials!=null && loginCredentials.getPhoneNumber()!=null){
			query="User.login_by_phone";
		}
		logger.info("UserDaoImpl_MyBatis - > login:: query: "+query);
		int count=session.selectOne(query,loginCredentials);
		logger.info("UserDaoImpl_MyBatis - > login:: user found:"+count);
		return (count==1) ? true: false;
	}
	
	
	
	@Override
	public boolean isUserRegistered(String userDeviceId){
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("UserDaoImpl_MyBatis - > isUserRegistered::");
		User userVO=session.selectOne("User.isUserRegistered", userDeviceId);
		logger.info("UserDaoImpl_MyBatis - > Found User ::"+userVO);
		return (userVO!=null) ? true : false;
	}
	
	@Override
	public boolean registerUser(User userVO) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		session.insert("User.saveUser", userVO);
		logger.info("UserDaoImpl_MyBatis - > registerUser:: User saved "+userVO.getId());
		logger.info("UserDaoImpl_MyBatis - > registerUser:: Saving Location ");
		session.insert("Device.insertLocation", userVO);
		session.commit();
		session.close();
		return true;
	}
	
	@Override
	public boolean updateUserProfile(User userVO) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		session.update("User.updateUser",userVO);
		logger.info("UserDaoImpl_MyBatis - > registerUser:: User saved "+userVO);
		session.commit();
		session.close();
		return true;
	}
	
	
	@Override
	public int findUserByName(String name) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		int count=session.selectOne("User.selectUserByName",name);
		logger.info("UserDaoImpl_MyBatis - > total found users by name are : "+count);
		session.commit();
		session.close();
		return count;
	}
	
	@Override
	public int findUserByEmail(String email) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		int count=session.selectOne("User.selectUserByEmail",email);
		logger.info("UserDaoImpl_MyBatis - > total found users by eamil are : "+count);
		session.commit();
		session.close();
		return count;
	}
	
	@Override
	public int findUserByPhone(String phoneNumber) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		int count=session.selectOne("User.selectUserByPhone",phoneNumber);
		logger.info("UserDaoImpl_MyBatis - > total found users by phone are : "+count);
		session.commit();
		session.close();
		return count;
	}

}
