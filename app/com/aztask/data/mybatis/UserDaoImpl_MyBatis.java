package com.aztask.data.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.UserDao;
import com.aztask.vo.Login;
import com.aztask.vo.NearbyUser;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

public class UserDaoImpl_MyBatis implements UserDao{
	
	ALogger logger=play.Logger.of(this.getClass());

	public UserDaoImpl_MyBatis() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public List<NearbyUser> getNearbyUsers(TaskVO task) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		List<NearbyUser> users=session.selectList("User.getNearbyUsers", task);//''selectList("Task.getTaskById", userId);
		logger.info("UserDaoImpl_MyBatis - > getNearbyUsers:: number of nearby users "+users.size());
		return users;
	}
	
	@Override
	public UserVO getUserById(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("UserDaoImpl_MyBatis - > getUserById::");
		UserVO userVO=session.selectOne("User.getUserById", userId);
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
		UserVO userVO=session.selectOne("User.isUserRegistered", userDeviceId);
		logger.info("UserDaoImpl_MyBatis - > Found User ::"+userVO);
		return (userVO!=null) ? true : false;
	}
	
	@Override
	public boolean registerUser(UserVO userVO) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		session.insert("User.saveUser", userVO);
		logger.info("UserDaoImpl_MyBatis - > registerUser:: User saved "+userVO.getId());
		session.commit();
		session.close();
		return false;
	}
	
	@Override
	public boolean updateUserProfile(UserVO userVO) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		session.update("User.updateUser",userVO);
		logger.info("UserDaoImpl_MyBatis - > registerUser:: User saved "+userVO);
		session.commit();
		session.close();
		return true;
	}

}
