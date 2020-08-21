package com.litmos.next;

import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Litmos_ApiKey {
	public static void main(String[] args) throws JSONException {

		String apikey = "6e8a9336-ed24-4396-969a-d496ec0bec3c";
		Client client = Client.create();
		String source = "Infometry Inc";
		WebResource webResource =client.resource("https://api.litmos.com/v1.svc/users?source=Infoemrty");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("source", source); 

		//Get response from RESTful Server get(ClientResponse.class);
		ClientResponse response = null;
		response = webResource.queryParams(queryParams)
				.header("Accept", "application/json;charset=UTF-8")
				.header("apikey", apikey)
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
		  //String Active = jsonobject.getBoolean(Active);
		    String Email = jsonobject.getString("Email");
		    String AccessLevel = jsonobject.getString("AccessLevel");
		    String Brand = jsonobject.getString("Brand");
		    
		    System.out.println("Id = "+Id + " "+"UserName ="+ UserName+" "+FirstName+" "+LastName+" "+ Email+" "+AccessLevel+" "+Brand);
		}  
	}
}
