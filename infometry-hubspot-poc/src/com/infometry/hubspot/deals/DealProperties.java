package com.infometry.hubspot.deals;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DealProperties {
	
	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		
		String uri = "https://api.hubapi.com/properties/v1/deals/properties";
		Client client = Client.create();
		
		WebResource webResource =client.resource(uri);
		MultivaluedMapImpl params = new MultivaluedMapImpl();
//		params.add("properties", "");
//		params.add("associations", "");
		
		ClientResponse response = webResource.queryParams(params).header("Accept", "application/json")
				.header("Authorization", "Bearer "+token).get(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			String jsonStr = response.getEntity(String.class);
			System.out.println(jsonStr);
		}
		else {
			System.out.println(response);
		}
		
	}

}
