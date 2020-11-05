package com.infometry.hubsopt.contacts;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Pro {
	
	public static void main(String[] args)throws Exception {
		String token = HubspotConn.refreshAccessToken();
		
		String uri = "https://api.hubapi.com/crm/v3/properties/contacts";
		Client client = Client.create();
		
		WebResource webResource =client.resource(uri);
		
		ClientResponse response = webResource.header("Accept", "application/json")
				.header("Authorization", "Bearer "+token).get(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			 
			String jsonStr = response.getEntity(String.class);
			JSONObject jo = new JSONObject(jsonStr);
			JSONArray jsonObj = new JSONArray(jo.getJSONArray("results").toString());
			for(int i = 0;i<jsonObj.length();i++) {
				String s = jsonObj.getJSONObject(i).getJSONObject("modificationMetadata").get("readOnlyValue").toString();
				if(!Boolean.parseBoolean(s)) {
				System.out.println(jsonObj.getJSONObject(i).get("name")+"->fieldType: "+jsonObj.getJSONObject(i).get("fieldType"));}
			}
		}
		else {
			System.out.println(response);
		}
	}

}
