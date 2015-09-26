package com.aztask.controllers;

import com.aztask.business.Task;

import play.Logger.ALogger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class UserTasks extends Controller {

	public static ALogger log=play.Logger.of(com.aztask.controllers.UserTasks.class);

	@Transactional(readOnly=true)
	public static Result newTasks() throws Exception {
		log.info("info Testing Logging.");
		return ok(Json.toJson(new Task().newTasks()));
	}

	public static Result featuredTasks() throws Exception {

		return newTasks();

	}

	public static Result nearbyTasks() throws Exception {

		return newTasks();

	}
}