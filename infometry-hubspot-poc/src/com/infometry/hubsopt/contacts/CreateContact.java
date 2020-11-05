package com.infometry.hubsopt.contacts;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class CreateContact {

	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		JSONObject j = new JSONObject();
		JSONObject ja = new JSONObject();
		
		
		j.put("email", "myCreate331@gmail.com");
		j.put("firstname", "helloHub");
		j.put("address", "25 First Street");
		
		ja.put("properties", j);
		
		String uri = "https://api.hubapi.com/crm/v3/objects/contacts";
		Client client = Client.create();
		
//		WebResource webResource =client.resource(uri);
		
		ClientResponse response = client.resource(uri).header("Authorization", "Bearer "+token)
				.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, ja.toString());
	
		if(response.getStatus() == 201) {
			String jsonStr = response.getEntity(String.class);
			JSONObject jsonObj = new JSONObject(jsonStr);
			System.out.println(jsonObj);
		}
		else {
			System.out.println(response);
		}
	}
}
