package com.aztask.data.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;
import akka.util.Collections;

import com.aztask.data.TaskDao;
import com.aztask.vo.TaskVO;

public class TaskDaoImpl_MyBatis implements TaskDao{
	
	ALogger logger=play.Logger.of(this.getClass());

	public TaskDaoImpl_MyBatis() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int createTask(TaskVO task) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTaskById:: got ibatis session:: "+session);
		session.insert("Task.saveTask", task);
		session.commit();
		session.close();
		return task.getTask_id();
	}
	
	@Override
	public TaskVO getTaskById(int taskId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTaskById:: got ibatis session:: "+session);
		return session.selectOne("Task.getTaskById", taskId);
	}

	@Override
	public List<TaskVO> newTasks() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<TaskVO> tasksByUserId(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: user id "+userId);
		List<TaskVO> tasks=session.selectList("Task.tasksByUserId", userId);//''selectList("Task.getTaskById", userId);
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
}
