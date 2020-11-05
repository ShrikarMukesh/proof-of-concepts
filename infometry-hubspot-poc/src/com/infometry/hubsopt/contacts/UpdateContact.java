package com.infometry.hubsopt.contacts;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import javax.ws.rs.core.MediaType;
//import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import com.infometry.hubspot.HubspotConn;

public class UpdateContact {
	public static void main(String[] args) throws Exception {
		String token = HubspotConn.refreshAccessToken();

		Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
		methodsField.setAccessible(true);
		
		Field modifiersField = Field.class.getDeclaredField("modifiers");
	 
		modifiersField.setAccessible(true);

	
		modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

	
		String[] methods = {
				"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"
		};
		
		methodsField.set(null, methods);

		JSONObject jsonObj = new JSONObject();
		JSONObject prop = new JSONObject();
		prop.put("firstname", "helloHub123");
		prop.put("address", "28 First Street");
		jsonObj.put("properties", prop);


		ClientConfig config = new DefaultClientConfig();


		config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
		Client client = Client.create(config);

		WebResource webResource =client.resource("https://api.hubapi.com/crm/v3/objects/contacts/22951");
		//webResource.setProperty("X-HTTP-Method-Override", "PATCH");
		ClientResponse resp = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+token)
				.method("PATCH", ClientResponse.class, jsonObj.toString());
		System.out.println(resp.toString());
		if (resp.getStatus() == 200) {
			System.out.println(resp.getEntity(String.class).toString());
		}
	}

}
