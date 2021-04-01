package com.litmos.poc;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class json {

	public static void main(String[] args) throws JSONException {
		JSONObject json = new JSONObject(); 

		json.put("type", "object");
		json.put("$schema", "http://json-schema.org/draft-07/schema#");

		JSONObject propertiesJson = new JSONObject();

		JSONObject typeJson = new JSONObject();
		typeJson.put("type", "string");

		propertiesJson.put("UserId", typeJson);
		json.put("properties", propertiesJson);


		String jsonStr = json.toString().replace("\\", "");
		System.out.println(jsonStr);
		
	} 
}
