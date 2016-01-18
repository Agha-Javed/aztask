package com.aztask.data;

import java.util.List;

import com.aztask.vo.Login;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

/**
 * @author Javed
 *
 */
public interface UserDao {
	
	public boolean registerUser(UserVO userVO);
	public boolean updateUserProfile(UserVO userVO);
	public boolean isUserRegistered(String userDeviceId);
	public boolean login(Login loginCredentials);
	public UserVO getUserById(int userId);
	public List<UserVO> findNearByUsers(TaskVO task);
}
