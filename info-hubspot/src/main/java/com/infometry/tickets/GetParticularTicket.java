package com.infometry.tickets;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class GetParticularTicket {


	static String uri = "https://api.hubapi.com/crm/v3/objects/tickets/190058190";
	static Client client = Client.create();
	static ClientResponse response = null;
	static JSONArray jsonArrayMain =  new JSONArray();
	static String token;
	static JSONObject jsonObj ;


	public static void main(String[] args)throws Exception {
		token = HubspotConn.refreshAccessToken();

		WebResource webResource =client.resource(uri);
		MultivaluedMapImpl params = new MultivaluedMapImpl();

		response = webResource.queryParams(params).header("Accept", "application/json")
				.header("Authorization", "Bearer "+token).get(ClientResponse.class);

		if(response.getStatus() == 200) {
			String jsonStr = response.getEntity(String.class);
			System.out.println(jsonStr);
		}
	}
}
