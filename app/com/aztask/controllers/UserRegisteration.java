package com.aztask.controllers;

import com.aztask.business.User;
import com.aztask.vo.Reply;
import com.aztask.vo.UserVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Javed
 * 
 *         This controller will receive json data and will register user.
 *
 */
public class UserRegisteration extends Controller {

	final static Logger.ALogger logger = Logger.of(UserRegisteration.class);

	/**
	 * The method will get json data to register user in a system.
	 * 
	 * @return success code.
	 */
	@BodyParser.Of(BodyParser.Json.class)
	@Transactional
	public static Result registerUser() throws Exception {
		JsonNode userNode = request().body().asJson();

		if (userNode.size() > 0) {
			ObjectMapper mapper = new ObjectMapper();
			UserVO user = mapper.treeToValue(userNode, UserVO.class);
			logger.info("Saving Object." + user);
			return ok(Json.toJson(new User().registerUser(user)));

		}

		return ok(Json.toJson(new Reply("400", "Invalid Request")));
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
	@Transactional(readOnly=true)
	public static Result isUserRegistered(int userId) throws Exception {
		
		logger.info("isUserRegistered :"+userId);
		
		JsonNode requestNode = request().body().asJson();
		if (requestNode.size() > 0) {
			String deviceId = requestNode.get("device-id").textValue();
			return ok(Json.toJson(new User().isUserRegistered(userId,deviceId)));
		}

		return ok(Json.toJson(new Reply("400", "Invalid Request")));

	}
}
