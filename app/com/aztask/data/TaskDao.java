package com.aztask.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import play.Logger.ALogger;
import play.db.DB;
import play.db.jpa.JPA;

import com.aztask.vo.TaskVO;

public class TaskDao {
	
	ALogger logger=play.Logger.of(this.getClass());

	public int createTask(TaskVO task){
		logger.info("createTask:: saving task for user");
		EntityManager entityManager=JPA.em();
		entityManager.persist(task);
		logger.info("createTask:: Task saved::"+task.getTask_id());
		return task.getTask_id()>0 ? task.getTask_id() : -1;
	}

	public List<TaskVO> newTasks() throws SQLException {
		DataSource ds = DB.getDataSource();
		Connection connection = ds.getConnection();
		System.out.println("Got DB Connection:" + ds.getConnection());

		String sql = "select * from t_task";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		List<TaskVO> tasks = new ArrayList<TaskVO>();
		while (resultSet.next()) {
			TaskVO task = new TaskVO();
			task.setTask_id(resultSet.getInt(1));
			task.setTask_desc(resultSet.getString(2));
			task.setTask_categories(resultSet.getString(3));
			task.setUser_id(resultSet.getInt(4));

			tasks.add(task);
		}
		System.out.println("Total Retrieved tasks:" + tasks.size());
		return tasks;
	}
	
	public TaskVO getTaskById(int taskId){
		logger.info("Findding Task by id:"+taskId);
		EntityManager entityManager=JPA.em();
		return entityManager.find(TaskVO.class, taskId);
	}
}
