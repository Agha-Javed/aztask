package com.aztask.business.test;

import static play.test.Helpers.running;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import play.test.Helpers;
import com.aztask.business.TaskBO;
import com.aztask.business.UserBO;
import com.aztask.vo.Login;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public class UserTest {

	public UserTest() {
	}
	
	

	@Test
	@Ignore
	public void registerUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				User userVO=new User("Hussain", "0166133886", "hussain@gmail.com", "programming","uuuttt12345");
				Reply reply = new UserBO().registerUser(userVO);
				System.out.println("Task create:" + reply);
			}
		});
	}// end method

	@Test
	@Ignore
	public void isUserRegisterd() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				Reply reply = new UserBO().isUserRegistered("abcdefg12345");
				System.out.println("User exists :" + reply.getMessage());
			}
		});
	}// end method
	
	
	@Test
	//@Ignore
	public void findNearByUsers() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				UserBO user=new UserBO();
				Task taskVO=new TaskBO().getTaskById(19);
				List<User> nearbyUsers=user.nearByUsers(taskVO);
				System.out.println("User exists :" + nearbyUsers.size());
			}
		});
	}// end method
	

	
	@Test
	@Ignore
	public void updateProfile() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				User userVO=new User("Hussain", "0166133896", "hussain_test@gmail.com", "programming;singing","uuuttt12345");
				Reply reply = new UserBO().updateUserProfile(userVO);
				System.out.println("Profile Updated create:" + reply.getMessage());
			}
		});
	}// end method
	
	
	@Test
	@Ignore
	public void login() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				UserBO user=new UserBO();
				Reply reply=user.login(new Login("hussain_test@gmail.com", "", "test", "uuuttt12345"));
				System.out.println("User exists :" + reply.getMessage());
			}
		});
	}// end method


}
