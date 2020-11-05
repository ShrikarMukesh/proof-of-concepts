package com.infometry.hubspot.companies;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class AssociateCompany {
	
	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		String uri = "https://api.hubapi.com/crm/v3/objects/companies/4286197619/associations/Deals/2766159553/6";
	
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
