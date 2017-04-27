package com.aztask.data.mybatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import play.Logger.ALogger;

import com.aztask.data.TaskDao;
import com.aztask.util.Util;
import com.aztask.vo.AssignedTask;
import com.aztask.vo.LikedTask;
import com.aztask.vo.Task;
import com.aztask.vo.User;

public class TaskDaoImpl_MyBatis implements TaskDao{
	
	ALogger logger=play.Logger.of(this.getClass());

	public TaskDaoImpl_MyBatis() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int createTask(Task task) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTaskById:: got ibatis session:: "+session);
		session.insert("Task.saveTask", task);
		session.commit();
		session.close();
		return task.getTask_id();
	}
	
	@Override
	public boolean deleteTask(int userId, int taskId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > deleteTask::");

		Map<String, Integer> params=new HashMap<String, Integer>();
		params.put("userId", userId);
		params.put("taskId", taskId);

		int recordDeleted=session.delete("Task.deleteTask", params);
		logger.info("Record deleted "+recordDeleted);
		
		session.commit();
		session.close();
		return (recordDeleted>0) ? true : false;
	}
	
	@Override
	public boolean unAssignTask(int userId, int taskId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > unAssignTask::");

		Map<String, Integer> params=new HashMap<String, Integer>();
		params.put("userId", userId);
		params.put("taskId", taskId);

		int recordDeleted=session.delete("Task.unAssignTask", params);
		logger.info("Record unassigned "+recordDeleted);
		
		session.commit();
		session.close();
		return (recordDeleted>0) ? true : false;
	}

	
	@Override
	public Task getTaskById(int taskId) {
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
	public List<Task> newTasks(){
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > newTasks:: fetching new tasks.");
		List<Task> newTasks=session.selectList("Task.newTasks");
		logger.info("Number of tasks returned."+newTasks.size());
		session.close();
		return newTasks;
	}
	
	@Override
	public List<Task> featuredTasks() {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > featuredTasks:: fetching featured tasks.");
		List<Task> featuredTasks=session.selectList("Task.featuredTasks");
		logger.info("Number of tasks returned."+featuredTasks.size());
		session.close();
		return featuredTasks;
	}
	
	
	@Override
	public List<Task> nearByTasks(int userId,String latitude,String longitude) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > nearByTasks:: fetching nearby tasks.");
		if( (latitude!=null && longitude!=null) && (latitude.length()>0 && latitude.length()>0) ){
			Map<String, String> params=new HashMap<String, String>();
			params.put("latitude", latitude);
			params.put("longitude", longitude);
			params.put("userId",""+userId);
			List<Task> nearByTasks=session.selectList("Task.nearByTasks",params);
			logger.info("Number of nearby tasks returned."+nearByTasks.size());
			session.close();
			return nearByTasks;
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public List<Task> getTasksByUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTasksByUser:: user id "+userId);
		List<Task> tasks=session.selectList("Task.allTasksOfUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
	@Override
	public List<Task> pendingTasksOfUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > pendingTasksOfUser:: user id "+userId);
		List<Task> tasks=session.selectList("Task.pendingTasksOfUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
	@Override
	public List<Task> acceptedTasksOfUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > acceptedTasks:: user id "+userId);
		List<Task> tasks=session.selectList("Task.acceptedTasksOfUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > tasksByUserId:: total found users "+tasks.size());
		return tasks;
	}
	
	@Override
	public List<Task> tasksByCategories(String categories) {
		if(categories!=null && categories.length()>0){
			SqlSession session=MyBatis_SessionFactory.openSession();

			String skillsClause=Util.getLikeClause("task_categories",categories);
			logger.info("TaskDaoImpl_MyBatis - > findNearByUsers:: skills to find for "+skillsClause);
			Map<String, String> params=new HashMap<String, String>();
			params.put("task_categories", skillsClause);

			List<Task> tasks=session.selectList("Task.tasksByCategories",params);
			session.close();
			return tasks;
		}
		return Collections.emptyList();
	}
	
	@Override
	public void assignTask(Task taskVO, List<User> users) {
		if (taskVO != null && users.size() > 0) {
			for (User userVO : users) {
				try {
					SqlSession session=MyBatis_SessionFactory.openSession();

					logger.info("Sending notification to " + userVO.getName());
					AssignedTask assignedTaskVO = new AssignedTask(taskVO.getUser_id(), userVO.getId(),taskVO.getTask_id(), 0);
					logger.info(assignedTaskVO.toString());
					session.insert("Task.assignTask", assignedTaskVO);
					logger.info("Record Saved sucessfuly.");
					session.commit();
					session.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// end loop
		}//end if
	}// end method
	
	@Override
	public AssignedTask getAssignedTaskVO(int taskId, int assigneeId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > acceptedTasks:: user id ");

		Map<String, Integer> params=new HashMap<String, Integer>();
		params.put("task_id", taskId);
		params.put("task_assignee_id", assigneeId);

		AssignedTask assignedTaskVO=session.selectOne("Task.getAssignedTask", params);//''selectList("Task.getTaskById", userId);
		logger.info("TaskDaoImpl_MyBatis - > assignedTaskVO:: "+assignedTaskVO);
		
		session.close();
		return assignedTaskVO;
	}
	
	@Override
	public List<Integer> getTasksByAssignee(int assigneeId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTasksByAssignee:: assignee id "+assigneeId);

		Map<String, Integer> params=new HashMap<String, Integer>();
		params.put("task_assignee_id", assigneeId);

		List<Integer> tasksList=session.selectList("Task.getTasksByAssignee", params);
		logger.info("TaskDaoImpl_MyBatis - > assignedTaskVO:: "+tasksList);
		
		session.close();
		return tasksList;
	}
	
	@Override
	public boolean acceptTask(AssignedTask assignedTaskVO) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > acceptedTasks:: user id ");

		int recordUpdated=session.update("Task.updateAssignedTask", assignedTaskVO);
		session.commit();
		
		session.close();
		return (recordUpdated>0) ? true : false;
	}

	@Override
	public boolean likeTask(LikedTask likedTaskVO) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > likeTask()");

		int recordUpdated=session.insert("Task.likeTask", likedTaskVO);
		session.commit();
		
		session.close();
		return (recordUpdated>0) ? true : false;
	}
	
	
	@Override
	public boolean unLikeTask(int userIdWhoUnLikedTask, int unLikedTaskId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > likeTask()");

		Map<String, Integer> params=new HashMap<String, Integer>();
		params.put("taskId", unLikedTaskId);
		params.put("userWhoUnLikedTask", userIdWhoUnLikedTask);

		int recordUpdated=session.insert("Task.unLikeTask", params);
		session.commit();
		
		session.close();
		return (recordUpdated>0) ? true : false;
	}
	
	@Override
	public List<Integer> getTasksLikedByUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > getTasksLikedByUser:: user id "+userId);

		Map<String, Integer> params=new HashMap<String, Integer>();
		params.put("user_id", userId);

		List<Integer> tasksList=session.selectList("Task.getTasksLikedByUser", params);
		logger.info("TaskDaoImpl_MyBatis - > assignedTaskVO:: "+tasksList);
		
		session.close();
		return tasksList;
	}
	
	
	@Override
	public List<Task> assignedTasksToUser(int userId) {
		SqlSession session=MyBatis_SessionFactory.openSession();
		logger.info("TaskDaoImpl_MyBatis - > assignedTasksToUser:: user id "+userId);
		
		List<Task> tasks=session.selectList("Task.assignedTasksToUser", userId);//''selectList("Task.getTaskById", userId);
		session.close();
		logger.info("TaskDaoImpl_MyBatis - > assignedTasksToUser:: total found assgined tasks "+tasks.size());
		return tasks;
	}

	
	
	
	
}
