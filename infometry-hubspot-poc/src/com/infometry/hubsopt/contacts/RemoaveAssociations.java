package com.infometry.hubsopt.contacts;

import javax.ws.rs.core.MediaType;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class RemoaveAssociations {
	
	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
				
				String uri = "https://api.hubapi.com/crm/v3/objects/contacts/22701/associations/deals/2785051607/4";
				Client client = Client.create();
				
				ClientResponse response = client.resource(uri).header("Authorization", "Bearer "+token)
						.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
				
				if(response.getStatus() == 204) {
					System.out.println("deleted");
				}else {
					System.out.println(response);
				}
			}

}
