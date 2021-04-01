package batchUpdate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

public class UpdateBatch {

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

		JSONObject j = new JSONObject();
		JSONObject j1 = new JSONObject();
		JSONObject j2 = new JSONObject();
		JSONObject ja = new JSONObject();
		JSONObject ja1 = new JSONObject();
		JSONObject ja2 = new JSONObject();
		JSONArray jr = new JSONArray();



		j.put("firstname", "helloHub456");

		j1.put("firstname", "helloHub234");

		j2.put("firstname", "helloHub654");

		ja.put("id", "351");
		ja.put("properties", j);
		ja1.put("id", "401");
		ja1.put("properties", j1);
		ja2.put("id", "451");
		ja2.put("properties", j2);

		jr.put(ja);
		jr.put(ja1);
		jr.put(ja2);

		JSONObject finalObj = new JSONObject();
		finalObj.put("inputs", jr);

		//		System.out.println(finalObj);

		ClientConfig config = new DefaultClientConfig();


		config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
		Client client = Client.create(config);

		WebResource webResource =client.resource("https://api.hubapi.com/crm/v3/objects/contacts/batch/update");
		//webResource.setProperty("X-HTTP-Method-Override", "PATCH");
		ClientResponse resp = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+token)
				.method("POST", ClientResponse.class, finalObj.toString());
		System.out.println(resp.toString());
		if (resp.getStatus() == 200) {
			System.out.println(resp.getEntity(String.class).toString());
		}
	}

}

