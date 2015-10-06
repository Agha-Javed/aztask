package com.aztask.business.test;

import java.util.List;

import org.junit.*;

import play.db.jpa.JPA;
import play.test.Helpers;

import com.aztask.business.Task;
import com.aztask.vo.TaskVO;

import static play.test.Helpers.*;

public class TaskTest{

//	@Test
//	public void testCreateTask() {
//		running(Helpers.fakeApplication(), new Runnable() {
//		    public void run() {
//			JPA.withTransaction(new play.libs.F.Callback0() {
//			    public void invoke() {
//					TaskVO taskVO=new TaskVO();
//					int taskId=new Task().createTask(taskVO);
//					System.out.println("Task create:"+taskId);
//			    }
//			});
//		    }
//		});
//	    }
	
	@Test
	@Ignore
	public void getTaskById() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				TaskVO taskVO = new TaskVO();
				taskVO = new Task().getTaskById(6);
				System.out.println("Task create:" + taskVO);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void createTask() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				TaskVO taskVO = new TaskVO("Task Test Description", "general", 1, "37.386337", "-122.085823", "abcd12345");
				int taskId = new Task().createTask(taskVO);
				System.out.println("Task created:" + taskId);
			}
		});
	}// end method
	

	@Test
	public void getTasksById() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks= new Task().getTasksByUserId(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method

	

}
