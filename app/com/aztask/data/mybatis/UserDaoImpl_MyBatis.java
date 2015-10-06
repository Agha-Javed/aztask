package com.aztask.data.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.UserDao;
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
	public List<TaskVO> tasksByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
