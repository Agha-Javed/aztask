package com.aztask.business.test;

import static play.test.Helpers.running;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import play.test.Helpers;

import com.aztask.business.Task;
import com.aztask.business.User;
import com.aztask.data.mybatis.TaskDaoImpl_MyBatis;
import com.aztask.vo.NearbyUser;
import com.aztask.vo.Reply;
import com.aztask.vo.TaskVO;
import com.aztask.vo.UserVO;

public class UserTest {

	public UserTest() {
	}
	
	

	@Test
	@Ignore
	public void registerUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				UserVO userVO=new UserVO("Hussain", "0166133886", "hussain@gmail.com", "programming","uuuttt12345");
				Reply reply = new User().registerUser(userVO);
				System.out.println("Task create:" + reply);
			}
		});
	}// end method

	@Test
	@Ignore
	public void isUserRegisterd() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				Reply reply = new User().isUserRegistered("abcdefg12345");
				System.out.println("User exists :" + reply.getMessage());
			}
		});
	}// end method
	
	
	@Test
	public void findNearByUsers() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				User user=new User();
				TaskVO taskVO=new Task().getTaskById(19);
				List<NearbyUser> nearbyUsers=user.getNearbyUsers(taskVO);
				System.out.println("User exists :" + nearbyUsers.size());
			}
		});
	}// end method


}
