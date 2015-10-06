package com.aztask.data;

import java.util.List;
import com.aztask.vo.NearbyUser;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

/**
 * @author Javed
 *
 */
public interface UserDao {
	
	public boolean registerUser(UserVO userVO);
	public boolean isUserRegistered(String userDeviceId);
	public List<TaskVO> tasksByUserId(int userId);
	public UserVO getUserById(int userId);
	public List<NearbyUser> getNearbyUsers(TaskVO task);
}
