package com.litmos.poc;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ListofAchivement {

	public static void main(String[] args) throws JSONException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject(); 

		json.put("type", "object");
		json.put("$schema", "http://json-schema.org/draft-07/schema#");

		JSONObject propertiesJson = new JSONObject();

		JSONObject typeJson = new JSONObject();
		typeJson.put("type", "string");

		JSONObject typeBoolean = new JSONObject();
		typeBoolean.put("type", "boolean");

		JSONObject typeInteger = new JSONObject();
		typeInteger.put("type", "integer");

		propertiesJson.put("UserId",typeJson);
		propertiesJson.put("Title", typeJson);
		propertiesJson.put("Description", typeJson);
		propertiesJson.put("AchievementDate", typeBoolean);
		propertiesJson.put("CourseId", typeBoolean);
		propertiesJson.put("CompliantTillDate", typeInteger);
		propertiesJson.put("Score", typeJson);
		propertiesJson.put("Result", typeJson);
		propertiesJson.put("Type", typeJson);
		propertiesJson.put("FirstName", typeJson);
		propertiesJson.put("LastName", typeInteger);
		propertiesJson.put("AccessTillDate", typeJson);
		propertiesJson.put("AchievementId", typeJson);
		propertiesJson.put("CertificateId", typeJson);
		json.put("properties", propertiesJson);
		
		System.out.println(propertiesJson);	
		array.put(propertiesJson);
		System.out.println(array);
	}
}
