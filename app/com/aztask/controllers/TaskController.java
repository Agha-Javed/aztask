package com.aztask.controllers;

import com.aztask.business.TaskBO;
import com.aztask.service.TaskService;
import com.aztask.vo.Reply;
import com.aztask.vo.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger.ALogger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class TaskController extends Controller {

	public static ALogger logger = play.Logger.of(TaskController.class);


	public static Result newTasks() throws Exception {
		logger.info("info Testing Logging.");
		TaskService taskService = TaskService.getInstance();
		return ok(Json.toJson(taskService.newTasks()));
	}

	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result createTask(int userId) throws Exception {

		logger.info("Creating task for user ::" + userId);

		JsonNode taskNode = request().body().asJson();

		if (taskNode.size() > 0) {
			TaskService taskService = TaskService.getInstance();
			ObjectMapper mapper = new ObjectMapper();
			Task task = mapper.treeToValue(taskNode, Task.class);
			task.setUser_id(userId);
			logger.info("Saving Task Object." + task);
			return ok(Json.toJson(taskService.createTask(task)));
		}

		return ok(Json.toJson(new Reply("200", "You task is being processed.")));
	}

	public static Result updateTask() {
		return ok(Json.toJson(new Reply("200", "Task Updated")));
	}

	public static Result deleteTask(int userId,int taskId) {
		logger.info("User Id." + userId+" and TaskId "+taskId);
		return ok(Json.toJson(TaskService.getInstance().deleteTask(userId, taskId)));
	}

	public static Result userTasksById(int userId) {
		logger.info("Got request to show user tasks -> user_id " + userId);
		return ok(Json.toJson(TaskService.getInstance().allTasksOfUser(userId)));
	}

	public static Result acceptTask(int userId, int taskId) throws Exception {
		logger.info("Accepting task.");
		logger.info("User " + userId + " is accepting task:" + taskId);
		return ok(Json.toJson(TaskService.getInstance().acceptTask(userId, taskId)));
	}

	public static Result taskById(int taskId) throws Exception {
		logger.info("info Testing Logging.");
		return ok(Json.toJson(new TaskBO().getTaskById(taskId)));
	}

	public static Result featuredTasks() throws Exception {

		return newTasks();

	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result nearbyTasks() throws Exception {
		logger.info("TaskController.tasksByCateogories.");
		JsonNode taskNode = request().body().asJson();
		if(taskNode.size()>0){
			String latitude=taskNode.get("latitude").asText();
			String longitude=taskNode.get("longitude").asText();

			logger.info("Latitude."+latitude);
			logger.info("longitude."+longitude);

			return ok(Json.toJson(TaskService.getInstance().nearByTasks(latitude,longitude)));
		}
		return ok(Json.toJson(new Reply("200", "You task is being processed.")));
	}
	
	public static Result tasksByCateogories() throws Exception{
		logger.info("TaskController.tasksByCateogories.");
		JsonNode taskNode = request().body().asJson();
		if(taskNode.size()>0){
			String categories=taskNode.findValue("task_categories").asText();
			logger.info("TaskController.tasksByCateogories."+categories);
			return ok(Json.toJson(TaskService.getInstance().tasksByCategories(categories)));
		}
		return ok(Json.toJson(new Reply("200", "You task is being processed.")));
	}

}
