package com.aztask.controllers;

import com.aztask.service.UserService;
import com.aztask.util.Constants;
import com.aztask.util.JSONValidationUtil;
import com.aztask.vo.Login;
import com.aztask.vo.Reply;
import com.aztask.vo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller{
	final static Logger.ALogger logger = Logger.of(UserController.class);

	/**
	 * The method will get json data to register user in a system.
	 * 
	 * @return success code.
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result registerUser(){
		JsonNode userNode = request().body().asJson();

		if (userNode.size() > 0) {
			logger.info(userNode.toString());
			
			if(!JSONValidationUtil.validate(userNode.toString(), Constants.JSON_USER_SCHEMA)){
				logger.info("Invalid JSON Data.");
				return ok(Json.toJson(new Reply("401", "Invalid Request Data.")));
			}
			
			ObjectMapper mapper = new ObjectMapper();
			User user;
			try {
				user = mapper.treeToValue(userNode, User.class);
				logger.info("Registering User." + user);
				logger.info("Device Info." + user.getDeviceInfo());
				return ok(UserService.getInstance().registerUser(user));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return ok("{\"code\":\"400\",\"id\":\"0\"}");
	}

	

	/**
	 * Once user is registered, this method helps to update profile after registeration. 
	 * 
	 * @return success code.
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result updateUserProfile(){
		JsonNode userNode = request().body().asJson();

		if (userNode.size() > 0) {
			ObjectMapper mapper = new ObjectMapper();
			User user;
			try {
				user = mapper.treeToValue(userNode, User.class);
				logger.info("Updating User Profile." + user);
				return ok(Json.toJson(UserService.getInstance().updateUserProfile(user)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
		}

		return ok(Json.toJson(new Reply("400", "Invalid Request")));
	}

	
	/**
	 * If user wants create any task, he/she needs to login, and this method helps in same. 
	 * 
	 * @return success code.
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result login(){
		JsonNode loginNode = request().body().asJson();

		if (loginNode.size() > 0) {
			ObjectMapper mapper = new ObjectMapper();
			Login userLoginCredentials;
			try {
				userLoginCredentials = mapper.treeToValue(loginNode, Login.class);
				logger.info("Login." + userLoginCredentials);
				return ok(Json.toJson(UserService.getInstance().login(userLoginCredentials)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return ok(Json.toJson(new Reply("400", "Invalid Username/Password")));
	}

	/**
	 * This method will check, whether the device is registered or not. if it is
	 * not registered then user will be navigated to registerion screen
	 * otherwise sign-in scree/home screen.
	 * 
	 * @return Result
	 * @throws Exception
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result isUserRegistered(String deviceId) throws Exception {
		logger.info("Checking if device "+deviceId+" exists.");

		if (deviceId!=null && deviceId.length()>0) {
//			return ok(Json.toJson(UserService.getInstance().isUserRegistered(deviceId)));

			return ok(UserService.getInstance().isUserRegistered(deviceId));

		}
		return ok(Json.toJson(new Reply("400", "Device is not registered.")));
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result registerGCMToken(int userId) throws Exception {
		logger.info("Registring gcm token for "+userId+" user.");
		if (userId>0) {
			JsonNode requestNode = request().body().asJson();
			String gcmToken=requestNode.get("token").asText();
			logger.info("GCM Token "+gcmToken);
			return ok(Json.toJson(UserService.getInstance().registerGCMToken(userId,gcmToken)));
		}
		return ok(Json.toJson(new Reply("400", "Invalid User.")));
	}
	

}
