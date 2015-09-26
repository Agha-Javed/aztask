package com.aztask.business.test;

import org.junit.*;

import play.db.jpa.JPA;
import play.libs.F;
import play.test.FakeApplication;
import play.test.Helpers;

import com.aztask.business.Task;
import com.aztask.vo.TaskVO;

import static play.test.Helpers.*;

public class TaskTest{

	@Test
	public void testCreateTask() {
		running(Helpers.fakeApplication(), new Runnable() {
		    public void run() {
			JPA.withTransaction(new play.libs.F.Callback0() {
			    public void invoke() {
					TaskVO taskVO=new TaskVO();
					int taskId=new Task().createTask(taskVO);
					System.out.println("Task create:"+taskId);
			    }
			});
		    }
		});
	    }
	
}
