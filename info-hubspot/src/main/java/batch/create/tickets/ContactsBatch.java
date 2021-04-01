package batch.create.tickets;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class ContactsBatch {
	public static void main(String[] args) throws Exception {

		//String token = HubspotConn.refreshAccessToken();
		String request = requestBody();

		/*String uri = "https://api.hubapi.com/crm/v3/objects/tickets";
		Client client = Client.create();

		ClientResponse response = client.resource(uri).header("Authorization", "Bearer "+token)
				.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request.toString());

		if(response.getStatus() == 201) {
			String jsonStr = response.getEntity(String.class);
			JSONObject jsonObj = new JSONObject(jsonStr);
			System.out.println(jsonObj);
		}
		else {
			System.out.println(response);
		}*/
	}

	private static String requestBody() throws JSONException {
		
		JSONObject outer = new JSONObject();
		JSONArray inputs = new JSONArray();
		JSONObject inputsOutput = new JSONObject();
		
		JSONObject properties1 = new JSONObject();
		properties1.put("firstname", "Nirmala");
		properties1.put("lastname", "Sitaraman");
		properties1.put("email", "nirmala@gmail.com");
		properties1.put("age", "23");
		
		JSONObject properties2 = new JSONObject();
		properties2.put("firstname", "Piyush");
		properties2.put("lastname", "Goval");
		properties2.put("email", "Piyush@gmail.com");
		properties2.put("age", "23");
		
		
		inputsOutput.put("properties", properties1);
		inputsOutput.put("properties", properties2);
		System.out.println(inputsOutput);
		
		return null;
	}
}