package com.aztask.data;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.Logger.ALogger;
import play.db.jpa.JPA;

import com.aztask.vo.NearbyUser;
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
	
	/**
	 * @param task , this task is newly added by users, so we need to find all users nearby on the basis of coordinates.
	 * @return
	 */
	public List<NearbyUser> getNearbyUsers(TaskVO task) {

		String query="SELECT device_id, (6371 * acos (cos ( radians(37.386339) ) * cos( radians(tul.latitude ) ) * cos( radians(tul.longitude ) - radians(-122.085823) )+sin ( radians(37.386339) )* sin( radians(tul.latitude) ))) AS distance" + 
				" FROM t_user_loc tul having distance < 1 ORDER BY distance";

//		String query="SELECT device_id, (6371 * acos (cos ( radians(:latitude) ) * cos( radians(tul.latitude ) ) * cos( radians(tul.longitude ) - radians(:longitude) )+sin ( radians(:longitude) )* sin( radians(tul.latitude) ))) AS distance"+
//				 " FROM t_user_loc tul having distance < 1 order by distance";

//		String query="SELECT device_id, (6371 * acos (cos ( radians(?) ) * cos( radians(tul.latitude ) ) * cos( radians(tul.longitude ) - radians(?) )+sin ( radians(?) )* sin( radians(tul.latitude) ))) AS distance"+
//				 " FROM t_user_loc tul having distance < 1 order by distance";

		
		if (task != null) {
//			String latitude = task.getLatitude();
//			String longitude = task.getLongtitude();

			float latitude =Float.parseFloat(task.getLatitude());
			float longitude =Float.parseFloat(task.getLongtitude());

			log.info("Latitude:"+latitude);
			log.info("Langitude:"+longitude);
			Query queryRef = JPA.em().createNativeQuery(query);
//			Query queryRef = JPA.em().createNativeQuery(query);

//			queryRef.setParameter(1, latitude);
//			queryRef.setParameter(2, longitude);
//			queryRef.setParameter(3, longitude);

			return queryRef.getResultList();
		}

		return Collections.emptyList();
	}

}
