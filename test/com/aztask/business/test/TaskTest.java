package com.aztask.business.test;

import java.util.List;

import org.junit.*;

import play.test.Helpers;

import com.aztask.business.Task;
import com.aztask.service.TaskService;
import com.aztask.vo.NearByDevice;
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
	@Ignore
	public void getTasksById() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks= new Task().allTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method


	@Test
	@Ignore
	public void newTasks() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks=TaskService.getInstance().newTasks();
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method


	@Test
	@Ignore
	public void featuredTasks() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks=TaskService.getInstance().featuredTasks();
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method
	
	@Test
	//@Ignore
	public void nearByTasks() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				NearByDevice nearBy=new NearByDevice("abcdefg12345", 37.386337f, -122.085823f);
				List<TaskVO> tasks=TaskService.getInstance().nearByTasks(nearBy);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void allTasksOfUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks= TaskService.getInstance().allTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void pendingTasksOfUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks= TaskService.getInstance().pendingTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void acceptedTasksOfUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<TaskVO> tasks= TaskService.getInstance().acceptedTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method


}
