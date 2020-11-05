package com.infometry.hubspot.companies;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class CreateCompany {

	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		JSONObject j = new JSONObject();
		JSONObject ja = new JSONObject();
		
		j.put("name", "Fitst");
		j.put("annualrevenue", "5000000000");
		j.put("city", "bengaluru");
		j.put("country", "India");
		j.put("description", "business intelligence");
		j.put("industry", "java");
		j.put("numberofemployees", "102");
		j.put("phone", "743897343");
		j.put("zip", "433221");
		j.put("state", "kr");
		ja.put("properties", j);
		
		String uri = "https://api.hubapi.com/crm/v3/objects/companies";
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
