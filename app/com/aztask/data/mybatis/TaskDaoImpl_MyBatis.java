package com.aztask.data.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.TaskDao;
import com.aztask.vo.NearByDevice;
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
		logger.info("TaskDaoImpl_MyBatis - > getTaskById:: fetching task by Id."+taskId);
		return session.selectOne("Task.getTaskById", taskId);
	}

	/**
	     This method won't be returning entire list of tasks from table
		 in fact, there would be way which will decide number of new task to be returned
		 like, all the tasks created in last 5 mins.

	 */
	@Override
	public List<TaskVO> newTasks(){
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > newTasks:: fetching new tasks.");
		List<TaskVO> newTasks=session.selectList("Task.newTasks");
		logger.info("Number of tasks returned."+newTasks.size());
		session.close();
		return newTasks;
	}
	
	@Override
	public List<TaskVO> featuredTasks() {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > featuredTasks:: fetching featured tasks.");
		List<TaskVO> featuredTasks=session.selectList("Task.featuredTasks");
		logger.info("Number of tasks returned."+featuredTasks.size());
		session.close();
		return featuredTasks;
	}
	
	
	@Override
	public List<TaskVO> nearByTasks(NearByDevice nearby) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > nearByTasks:: fetching nearby tasks.");
		List<TaskVO> nearByTasks=session.selectList("Task.nearByTasks",nearby);
		logger.info("Number of tasks returned."+nearByTasks.size());
		session.close();
		return nearByTasks;
	}
	
	@Override
	public List<TaskVO> getTasksByUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTasksByUser:: user id "+userId);
		List<TaskVO> tasks=session.selectList("Task.allTasksOfUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
	@Override
	public List<TaskVO> pendingTasksOfUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > pendingTasksOfUser:: user id "+userId);
		List<TaskVO> tasks=session.selectList("Task.pendingTasksOfUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
	@Override
	public List<TaskVO> acceptedTasksOfUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > acceptedTasks:: user id "+userId);
		List<TaskVO> tasks=session.selectList("Task.acceptedTasksOfUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
}
