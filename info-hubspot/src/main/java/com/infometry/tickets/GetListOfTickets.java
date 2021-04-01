package com.infometry.tickets;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class GetListOfTickets {
	static String uri = "https://api.hubapi.com/crm/v3/objects/tickets";
	static Client client = Client.create();
	static ClientResponse response = null;
	static JSONArray jsonArrayMain =  new JSONArray();
	static String token;
	static JSONObject jsonObj ;


	public static void main(String[] args)throws Exception {
		token = HubspotConn.refreshAccessToken();

		WebResource webResource =client.resource(uri);
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("limit", "100");
		//		params.add("properties", "");

		response = webResource.queryParams(params).header("Accept", "application/json")
				.header("Authorization", "Bearer "+token).get(ClientResponse.class);

		if(response.getStatus() == 200) {
			String jsonStr = response.getEntity(String.class);

			jsonObj = new JSONObject(jsonStr);

			JSONArray jsonArray = (JSONArray)jsonObj.get("results");
			for(int k = 0; k<jsonArray.length() ; k++) {
				jsonArrayMain.put(jsonArray.getJSONObject(k));
				System.out.println(jsonArray.get(k));
			}
		}else {
			System.out.println(response);
		}

		if(jsonObj.has("paging")) {
			uri = jsonObj.getJSONObject("paging").getJSONObject("next").getString("link");
		}
		else {
			uri = null;
		}

		if(jsonObj.has("paging")) {
			String link = jsonObj.getJSONObject("paging").getJSONObject("next").getString("link");
			recursiveMethodCall(link);
		}
		System.out.println("execurion completed");

	}

	private static void recursiveMethodCall(String link) throws Exception {
		WebResource webResource =client.resource(link);

		response = webResource.header("Accept", "application/json")
				.header("Authorization", "Bearer "+token).get(ClientResponse.class);

		if(response.getStatus() == 200) {
			String jsonStr = response.getEntity(String.class);

			jsonObj = new JSONObject(jsonStr);

			JSONArray jsonArray = (JSONArray)jsonObj.get("results");
			for(int k = 0; k<jsonArray.length() ; k++) {
				jsonArrayMain.put(jsonArray.getJSONObject(k));
				System.out.println(jsonArray.get(k));
			}
		}else {
			System.out.println(response);
		}

		if(jsonObj.has("paging")) {
			uri = jsonObj.getJSONObject("paging").getJSONObject("next").getString("link");
		}
		else {
			uri = null;
		}

		if(jsonObj.has("paging")) {
			link = jsonObj.getJSONObject("paging").getJSONObject("next").getString("link");
			recursiveMethodCall(link);
		}
		else {
			return;
		}

	}
}
