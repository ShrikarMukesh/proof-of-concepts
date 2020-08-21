package com.litmos.next;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Litmos_Header {
	public static void main(String[] args) throws JSONException {
		Client client = Client.create();
		client.getProperties();
		
		WebResource webResource =   client.resource("https://api.litmos.com/v1.svc/users?source=InfometryInc");

		ClientResponse response = webResource    
				.header("Accept", "application/json")           
				.header("apikey", "6e8a9336-ed24-4396-969a-d496ec0bec3c")
				.get(ClientResponse.class);

		String jsonStr = response.getEntity(String.class);
		System.out.println(jsonStr);

		JSONArray jsonarray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);

			String Id = jsonobject.getString("Id");
			String UserName = jsonobject.getString("UserName");
			String FirstName = jsonobject.getString("FirstName");
			String LastName = jsonobject.getString("LastName");
			boolean Active = jsonobject.getBoolean("Active");
			String Email = jsonobject.getString("Email");
			String AccessLevel = jsonobject.getString("AccessLevel");
			String Brand = jsonobject.getString("Brand");

			System.out.println("Id = "+Id + " "+"UserName = "+ UserName+" "+FirstName+" "+LastName+" "+" "+Active+ " "+ Email+" "+AccessLevel+" "+Brand);
		}

	}
}
