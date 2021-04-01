package batchdelete;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.infometry.hubspot.HubspotConn;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class Delete_Deals_UsingBatch {
	String s = "Shrikar";
	public static void main(String[] args)throws Exception {

		
		
		String token = HubspotConn.refreshAccessToken();

		JSONArray jr = new JSONArray();
		JSONObject j = new JSONObject();
		JSONObject j1 = new JSONObject();
		JSONObject j2 = new JSONObject();
		JSONObject j3 = new JSONObject();
		JSONObject j4 = new JSONObject();

		j.put("id", "3101");
		j1.put("id", "3351");
		j2.put("id", "3401");
		j3.put("id", "3451");
		j4.put("id", "3501");

		jr.put(j);
		jr.put(j1);
		jr.put(j2);
		jr.put(j3);
		jr.put(j4);

		JSONObject finalObj = new JSONObject();
		finalObj.put("inputs", jr);

		String uri = "https://api.hubapi.com/crm/v3/objects/deals/batch/archive";
		Client client = Client.create();

		ClientResponse response = client.resource(uri).header("Authorization", "Bearer "+token)
				.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, finalObj.toString());

		
		if(response.getStatus() == 204) {
			System.out.println("deleted");
			
		}
		else {
			System.out.println(response);
		}
		
	}
	private void pub() {
		

	}
	public void name() {
		this.pub();
		String sk = this.s;
	}

}
