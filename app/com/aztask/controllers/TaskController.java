package com.aztask.controllers;

import com.aztask.business.Task;
import com.aztask.service.TaskService;
import com.aztask.vo.AcceptedTaskVO;
import com.aztask.vo.Reply;
import com.aztask.vo.TaskVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger.ALogger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class TaskController extends Controller {

	public static ALogger logger = play.Logger.of(TaskController.class);

	@BodyParser.Of(BodyParser.Json.class)
	public static Result createTask(int userId) throws Exception {

		logger.info("Creating task for user ::" + userId);

		JsonNode taskNode = request().body().asJson();

		if (taskNode.size() > 0) {
			TaskService taskService = TaskService.getInstance();
			ObjectMapper mapper = new ObjectMapper();
			TaskVO task = mapper.treeToValue(taskNode, TaskVO.class);
			task.setUser_id(userId);
			logger.info("Saving Task Object." + task);
			return ok(Json.toJson(taskService.createTask(task)));
		}

		return ok(Json.toJson(new Reply("200", "You task is being processed.")));
	}

	public static Result updateTask() {
		return ok(Json.toJson(new Reply("200", "Task Updated")));
	}

	public static Result deleteTask() {
		return ok(Json.toJson(new Reply("200", "Task Deleted")));
	}

	public static Result userTasksById(int userId) {
		logger.info("Got request to show user tasks -> user_id " + userId);
		return ok(Json.toJson(new Task().tasksByUserId(userId)));
	}

	public static Result acceptTask(int userId, int taskId) throws Exception {
		logger.info("Accepting task.");
		logger.info("User " + userId + " is accepting task:" + taskId);
		return ok(Json.toJson(TaskService.getInstance().acceptTask(new AcceptedTaskVO(userId, taskId))));
	}

	public static Result newTasks() throws Exception {
		logger.info("info Testing Logging.");
		return ok(Json.toJson(new Task().newTasks()));
	}

	public static Result userTaskById() throws Exception {
		logger.info("info Testing Logging.");
		return ok(Json.toJson(new Task().newTasks()));
	}

	public static Result taskById(int taskId) throws Exception {
		logger.info("info Testing Logging.");
		return ok(Json.toJson(new Task().getTaskById(taskId)));
	}

	public static Result featuredTasks() throws Exception {

		return newTasks();

	}

	public static Result nearbyTasks() throws Exception {

		return newTasks();

	}

}
