package com.infometry.tickets;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class CreateTicket {
	public static void main(String[] args) throws Exception {
		
		String token = HubspotConn.refreshAccessToken();
		JSONObject j = new JSONObject();
		JSONObject ja = new JSONObject();
		
		j.put("hubspot_owner_id", "49925146");
		j.put("hs_pipeline", "0");
		j.put("hs_pipeline_stage", "1");
		j.put("hs_ticket_category", new String());
		j.put("hs_ticket_priority", "HIGH");
		j.put("subject", "troubleshoot report");

		ja.put("properties", j);
		System.out.println(ja);
		
		String uri = "https://api.hubapi.com/crm/v3/objects/tickets";
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
