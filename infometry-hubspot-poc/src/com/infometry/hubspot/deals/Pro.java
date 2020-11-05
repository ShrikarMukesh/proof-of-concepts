package com.infometry.hubspot.deals;

import org.codehaus.jettison.json.JSONArray;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Pro {
	
	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		
		String uri = "https://api.hubapi.com/properties/v1/deals/properties";
		Client client = Client.create();
		
		WebResource webResource =client.resource(uri);
		
		ClientResponse response = webResource.header("Accept", "application/json")
				.header("Authorization", "Bearer "+token).get(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			 
			String jsonStr = response.getEntity(String.class);
			JSONArray jsonObj = new JSONArray(jsonStr);
			for(int i = 0;i<jsonObj.length();i++) {
				String s = jsonObj.getJSONObject(i).get("readOnlyValue").toString();
				if(!Boolean.parseBoolean(s)) {
				System.out.println(jsonObj.getJSONObject(i).get("name"));}
			}
		}
		else {
			System.out.println(response);
		}
	}

}
