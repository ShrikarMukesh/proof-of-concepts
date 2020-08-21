package com.litmos.poc;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class Put {
	public static void main(String[] args) {

		Client client = Client.create();
		//JSONArray js = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("Id","ArgiLnL_ccY1");
			obj.put("UserName","Garetfffh");
			obj.put("FirstName","franfffk");
			obj.put("LastName","bale");
			obj.put("Active",true);
			obj.put("Email","mukeshrtrtr");
			obj.put("AccessLevel","Learner");
			obj.put("Brand","Default");
		}catch (JSONException e) {
			e.printStackTrace();
		}
		/*  String jsonData = client.resource("https://api.litmos.com/v1.svc/users?apikey=6E8A9336-ED24-4396-969A-D496EC0BEC3C&source=Infometry")
	    		 .type(MediaType.APPLICATION_JSON)
	    		 .post(ClientResponse.class,obj.toString())
	    		 .getEntity(String.class);;
	    		 System.out.println(jsonData);*/

		String jsonData1=client.resource("https://api.litmos.com/v1.svc/users?apikey=6E8A9336-ED24-4396-969A-D496EC0BEC3C&source=Infometry").path("ArgiLnL_ccY1")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, obj.toString())
				.getEntity(String.class);

		System.out.println(jsonData1);
		
	}
}
