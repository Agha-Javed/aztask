package com.aztask.data.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.UserDao;
import com.aztask.vo.Login;
import com.aztask.vo.NearByDevice;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

public class UserDaoImpl_MyBatis implements UserDao{
	
	ALogger logger=play.Logger.of(this.getClass());
	
	public UserDaoImpl_MyBatis() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public List<UserVO> findNearByUsers(TaskVO task) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		List<NearByDevice> nearByDevices=session.selectList("User.getNearbyDevices", task);//''selectList("Task.getTaskById", userId);
		logger.info("UserDaoImpl_MyBatis - > findNearByUsers:: number of nearby devices "+nearByDevices.size());
		String deviceIds=getWhereCluase(nearByDevices);
		logger.info("UserDaoImpl_MyBatis - > findNearByUsers:: near by devices "+deviceIds);
		String skills=getLikeClause(task);
		logger.info("UserDaoImpl_MyBatis - > findNearByUsers:: skills to find for "+skills);
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("deviceIds", deviceIds);
		params.put("skills", skills);

		List<UserVO> relatedUsers=session.selectList("User.selectNearbyUsers",params);
		return relatedUsers;
		//return session.getMapper(NearbyUserWrapper.class).nearByUsers(deviceIds, skills);
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
	
	
	public String getWhereCluase(List<NearByDevice> nearByUsers){
		StringBuffer whereCluase=new StringBuffer("(");
		for(NearByDevice nearByUser: nearByUsers){
			whereCluase.append("'"+nearByUser.getDevice_id()+"',");
		}
		whereCluase.deleteCharAt(whereCluase.length()-1);
		whereCluase.append(")");
		return whereCluase.toString();
	}
	
	public String getLikeClause(TaskVO taskVO){
		
		StringBuffer likeCluase=new StringBuffer();
		for(String taskCategory : taskVO.getTask_categories().split(";")){
			likeCluase.append(" skills like '%"+taskCategory+"%' or");
		}
		likeCluase.delete(likeCluase.length()-2, likeCluase.length());
		return likeCluase.toString();
		
	}


}
