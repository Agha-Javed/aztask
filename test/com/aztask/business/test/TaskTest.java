package com.aztask.business.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.*;

import play.test.Helpers;

import com.aztask.business.TaskBO;
import com.aztask.business.UserBO;
import com.aztask.service.TaskService;
import com.aztask.vo.Task;
import com.aztask.vo.User;

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
				Task taskVO = new Task();
				taskVO = new TaskBO().getTaskById(6);
				System.out.println("Task create:" + taskVO);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void createTask() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String currentTime = sdf.format(new Date(System.currentTimeMillis()));
				Task taskVO = new Task("Need photographer", "java", 1, "37.386337", "-122.085823", "abcd12345",currentTime);
				int taskId = new TaskBO().createTask(taskVO);
				System.out.println("Task created:" + taskId);
			}
		});
	}// end method
	

	@Test
	@Ignore
	public void getTasksById() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks= new TaskBO().allTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method


	@Test
	@Ignore
	public void newTasks() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks=TaskService.getInstance().newTasks();
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method


	@Test
	@Ignore
	public void featuredTasks() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks=TaskService.getInstance().featuredTasks();
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method
	
	@Test
	@Ignore
	public void nearByTasks() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				//NearByDevice nearBy=new NearByDevice("abcdefg12345", 37.386337f, -122.085823f);
				//String latitude="";
				List<Task> tasks=TaskService.getInstance().nearByTasks("37.386337","-122.085823");
				System.out.println("Got tasks:"+tasks.size());
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void allTasksOfUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks= TaskService.getInstance().allTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void pendingTasksOfUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks= TaskService.getInstance().pendingTasksOfUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method

	
	@Test
	@Ignore
	public void acceptedTasksOfUser() {
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks= TaskService.getInstance().tasksAcceptedByUser(2);
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method
	
	@Test
	@Ignore
	public void tasksByCategories(){
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				List<Task> tasks= TaskService.getInstance().tasksByCategories("java;education");
				System.out.println("Got tasks:"+tasks);
			}
		});
	}// end method
	
	
	@Test
//	@Ignore
	public void assignTask(){
		running(Helpers.fakeApplication(), new Runnable() {
			public void run() {
				TaskBO taskBO=new TaskBO();
				Task task=taskBO.getTaskById(134);
				User userVO=new UserBO().getUserById(1);
				List<User> usersList=new ArrayList<User>();
				usersList.add(userVO);
				taskBO.assignTask(task,usersList);
				System.out.println("Task Assigned:");
			}
		});
	}


}
