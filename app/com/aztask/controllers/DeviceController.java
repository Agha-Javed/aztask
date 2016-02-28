package com.aztask.controllers;

import com.aztask.service.DeviceService;
import com.aztask.util.Constants;
import com.aztask.util.JSONValidationUtil;
import com.aztask.vo.DeviceInfo;
import com.aztask.vo.Reply;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger.ALogger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class DeviceController extends Controller{

	public static ALogger logger = play.Logger.of(TaskController.class);
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result updateDeviceLocation(){
		
		logger.info("DeviceController.updateDeviceLocation() start.");
		JsonNode deviceInfoNode = request().body().asJson();
		
		if(deviceInfoNode.size()>0){

			ObjectMapper mapper = new ObjectMapper();
			try {

				if(!JSONValidationUtil.validate(deviceInfoNode.toString(), Constants.JSON_UPDATE_DEVICE_LOC_SCHEMA)){
					logger.info("Invalid JSON Data.");
					return ok(Json.toJson(new Reply("401", "Invalid Request Data.")));
				}
				
				DeviceInfo deviceInfo = mapper.treeToValue(deviceInfoNode, DeviceInfo.class);
				logger.info("Device Info." + deviceInfo);
				DeviceService.getInstance().updateDeviceLocation(deviceInfo);
				return ok(Json.toJson(new Reply("200", "Device Location Updated.")));
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return ok(Json.toJson(new Reply("401", "Device Location couldn't be updated.")));
	}


}
