package batch.create.tickets;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class TicketsBatch {
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
		JSONObject properties = new JSONObject();
		properties.put("hs_pipeline", "");
		properties.put("hs_pipeline", "");
		properties.put("hs_pipeline", "");
		properties.put("hs_pipeline", "");
		
		
		JSONObject j = new JSONObject();
		
		JSONObject ja = new JSONObject();

		j.put("hubspot_owner_id", "49925146");
		j.put("hs_pipeline", "0");
		j.put("hs_pipeline_stage", "1");
		j.put("hs_ticket_category", new String());
		j.put("hs_ticket_priority", "HIGH");
		j.put("subject", "troubleshoot report");

		ja.put("properties", j);
		System.out.println(ja);
		return null;
	}
}