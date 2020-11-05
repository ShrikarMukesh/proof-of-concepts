package com.infometry.hubspot.deals;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class AssociateDeal {
	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		String uri = "https://api.hubapi.com/crm/v3/objects/deals/2766159553/associations/Companies/4286197619/5";
	
		Client client = Client.create();
		
//		WebResource webResource =client.resource(uri);
		
		ClientResponse response = client.resource(uri).header("Authorization", "Bearer "+token)
				.type(MediaType.APPLICATION_JSON).put(ClientResponse.class);
		
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
