package com.aztask.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class JSONValidationUtil {

	
	public JSONValidationUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws Exception {
		
//		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(JSONValidationUtil.class.getResourceAsStream(Constants.JSON_USER_SCHEMA)));
//		ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(new InputStreamReader(JSONValidationUtil.class.getResourceAsStream(Constants.JSON_USER_SCHEMA).)

		String schema=readSchemaFile(Constants.JSON_USER_SCHEMA);
		String data="{\"contact\":\"0166144600\",\"email\":\"sajad@gmail.com\",\"skills\":\"Teaching\",\r\n" + 
				"\"deviceInfo\":{\"deviceId\":\"abcd12345\",\"latitude\" : \"36.386337\",\"longitude\":\"-121.085823\"}\r\n" + 
				"}";
		
		System.out.println(validate(data,schema));
	}
	
	public static String readSchemaFile(String fileName) {
		
		InputStream inputStream=JSONValidationUtil.class.getResourceAsStream(fileName);
	    StringBuilder sb = new StringBuilder(512);
	    try {
	        Reader r = new InputStreamReader(inputStream, "UTF-8");
	        int c = 0;
	        while ((c = r.read()) != -1) {
	            sb.append((char) c);
	        }
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    return sb.toString();
	}
	
	public static boolean validate(String jsonData, String jsonSchema) {
		JsonNode schemaNode;
		try {
			schemaNode = JsonLoader.fromString(readSchemaFile(jsonSchema));
			JsonNode data = JsonLoader.fromString(jsonData);

			JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			// load the schema and validate
			JsonSchema schema = factory.getJsonSchema(schemaNode);
			ProcessingReport report = schema.validate(data);
			return report.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
