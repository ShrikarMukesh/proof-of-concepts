package com.hubspot;

import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DataToJsonFile {
	private static FileWriter file;
	public static void main(String[] args) throws JSONException  {

		Client client = Client.create();
		WebResource webResource =client.resource("https://api.hubapi.com/crm/v3/objects/contacts");
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
		try {

			file = new FileWriter("‪‪D:\filename.txt");
			file.write(jsonObj.toString());
			
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
