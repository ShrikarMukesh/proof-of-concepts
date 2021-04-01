package com.infometry.hubspot;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class HubspotConn {
	
	public static void main(String[] args) {
		

		String generatedAccessToken;
		try {
			generatedAccessToken = refreshAccessToken();
			System.out.println("This is access token : "+generatedAccessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static String refreshAccessToken() throws Exception{
		String accessToken = "";
		//Response res = webresource.request().post(Entity.form(params), Response.class);
		Client client = Client.create();
		WebResource webresource = client.resource("https://api.hubapi.com/oauth/v1/token");
		
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		
		params.add("grant_type", "refresh_token");
		params.add("client_id", "557344b5-71a7-41be-a9a4-f4ef9e04316e");
		params.add("client_secret", "77f15274-b8e6-4b7b-a26b-fac33f4c8042");
		params.add("refresh_token", "ddab9d1b-cfc6-4322-8b9c-8b1ca746c1d9");

		ClientResponse resp = webresource.post(ClientResponse.class, params);
		
//		System.out.println(resp);
		try {
			if (resp.getStatus() == 200) {
				String json = resp.getEntity(String.class);
    			System.out.println(json);
				JSONObject jsonObj = new JSONObject(json);
				accessToken = jsonObj.getString("access_token");
//				int expiry = jsonObj.getInt("expires_in");
//				System.out.println(expiry);
			}
			else {
				int sType = resp.getStatus();
				
				throw new Exception(sType + " : " + resp.getClientResponseStatus().getReasonPhrase());
			}
		}
		finally {
			resp.close();
		}
		return accessToken;
	}

}
