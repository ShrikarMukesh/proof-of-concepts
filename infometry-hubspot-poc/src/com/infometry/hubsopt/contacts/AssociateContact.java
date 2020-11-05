package com.infometry.hubsopt.contacts;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class AssociateContact {
	
	public static void main(String[] args)throws Exception {
		
		String token = HubspotConn.refreshAccessToken();
		String uri = "https://api.hubapi.com/crm/v3/associations/contacts/companies/types?";
		
		Client client = Client.create();
		
        //WebResource webResource =client.resource(uri);
		
		ClientResponse response = client.resource(uri).header("Authorization", "Bearer "+token).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			
			String jsonStr = response.getEntity(String.class);
			JSONObject jsonObj = new JSONObject(jsonStr);
			System.out.println(jsonObj);
		}
		else {
			System.out.println(response);
		}
	}
}
