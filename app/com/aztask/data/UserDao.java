package com.aztask.data;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import play.Logger.ALogger;
import play.db.jpa.JPA;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

/**
 * @author Javed
 *
 */
public class UserDao {
	
	public static ALogger log=play.Logger.of(com.aztask.data.UserDao.class);


	public boolean registerUser(UserVO userVO){
		log.info("registerUser::"+userVO);
		EntityManager entityManager=JPA.em();
		entityManager.persist(userVO);
		log.info("User saved successfully::");
		return true;
	}
	
	public boolean isUserRegistered(int userId,String userDeviceId)throws SQLException{
		//TODO the parameter validation will go here.
		log.info("registerUser::"+userDeviceId);
		EntityManager entityManager=JPA.em();
		Query query = entityManager.createQuery("SELECT u FROM UserVO u where u.id=:user_id and u.deviceId = :device_id");
		@SuppressWarnings("unchecked")
		List<UserVO> userList=query.setParameter("user_id", userId).setParameter("device_id", userDeviceId).getResultList();
		log.info("Got user list::"+userList.size());
		return (userList!=null && userList.size()==1) ? true:false;
	}
	
	public List<TaskVO> tasksByUserId(int userId){
		Query query=JPA.em().createQuery("SELECT t FROM TaskVO t where t.user_id=:user_id");
		return query.setParameter("user_id", userId).getResultList();
	}

}
