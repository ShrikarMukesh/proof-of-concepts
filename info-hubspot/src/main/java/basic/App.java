package basic;



import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.codehaus.jettison.json.JSONObject;

public class App {

	public static void main( String[] args ) {
		Client client = ClientBuilder.newClient();
		WebTarget tokenTarget = client.target("https://api.hubapi.com/oauth/v1/token");

		MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
		params.add("grant_type", "refresh_token");
		params.add("client_id", "18a75f5b-4d02-4d04-849d-60bf4ed079eb");
		params.add("client_secret", "ad298ead-5a03-490b-9e49-d4e0e24cc65d");
		params.add("redirect_uri", "https://localhost");
		params.add("refresh_token", "18f2430e-b379-445c-b6e3-791dcf5bb154");

		String res;
		try {
			res = refreshAccessToken(client, tokenTarget, params);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	private static String refreshAccessToken (Client client, WebTarget tokenTarget, MultivaluedMap<String, String> params) throws Exception {
		String accessToken = "";
		Response res = tokenTarget.request().post(Entity.form(params), Response.class);
		System.out.println(res);
		try {
			if (res.getStatus() == 200) {
				JSONObject jsonObj = res.readEntity(JSONObject.class);
				accessToken = jsonObj.getString("access_token");
			}
			else {
				StatusType sType = res.getStatusInfo();
				throw new Exception(sType.getStatusCode() + " : " + sType.getReasonPhrase());
			}
		}
		finally {
			res.close();
		}
		return accessToken;
	}
}
