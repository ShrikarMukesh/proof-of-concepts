package com.hubspot;


import javax.ws.rs.core.MultivaluedMap;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Contacts {

	
	public static void main(String[] args) throws JSONException  {

		String uri = "https://api.hubapi.com/crm/v3/objects/contacts";
		Client client = Client.create();
		WebResource webResource =client.resource(uri);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("limit", "100");

		ClientResponse response = null;
		response = webResource.queryParams(queryParams)
				.header("Accept", "application/json")
				.header("Authorization", "Bearer CLedvOGaLhICAQEYgMjpASC7rP4EKLqmDTIZACAu9NAZMSy926Hu8SbQb8BzNa9BgwN2jjoXAAoCQQAADIADAAgAAAABAAAAAAAAABhCGQAgLvTQUmQaCo-R9JwaRYrvrcOPqtw-J5g" )
				.get(ClientResponse.class);
		
		String jsonStr = response.getEntity(String.class);
		
		System.out.println(jsonStr);

		JSONObject jsonObj = new JSONObject(jsonStr);
        
		String link = jsonObj.getJSONObject("paging").getJSONObject("next").getString("link");
		recursiveMethodCall(link);		
	}

	private static void recursiveMethodCall(String link) throws JSONException {
		
		if(link.length() ==0) {
			return;
			
		}else {
			Client client = Client.create();
			WebResource webResource =client.resource(link);
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			queryParams.add("limit", "100");

			ClientResponse response = null;
			response = webResource.queryParams(queryParams)
					.header("Accept", "application/json")
					.header("Authorization", "Bearer CLedvOGaLhICAQEYgMjpASC7rP4EKLqmDTIZACAu9NAZMSy926Hu8SbQb8BzNa9BgwN2jjoXAAoCQQAADIADAAgAAAABAAAAAAAAABhCGQAgLvTQUmQaCo-R9JwaRYrvrcOPqtw-J5g" )
					.get(ClientResponse.class);
			
			String jsonStr = response.getEntity(String.class);
			
			System.out.println(jsonStr);
			
			JSONObject jsonObj = new JSONObject(jsonStr);
			
	        
			String afterlink = jsonObj.getJSONObject("paging").getJSONObject("next").getString("link");
			recursiveMethodCall(afterlink);		
		}
	}
}