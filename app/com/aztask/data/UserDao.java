package com.aztask.data;

import java.util.List;

import com.aztask.vo.Login;
import com.aztask.vo.Task;
import com.aztask.vo.User;

/**
 * @author Javed
 *
 */
public interface UserDao {
	
	public User registerUser(User userVO);
	public boolean updateUserProfile(User userVO);
	public User isUserRegistered(String userDeviceId);
	public boolean login(Login loginCredentials);
	public User getUserById(int userId);
	public List<User> findNearByUsers(Task task);
	
	public int findUserByName(String name);
	public int findUserByPhone(String phoneNumber);
	public int findUserByEmail(String email);
}
