package basic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;

public class HubSpotCon {
	
	static String generatedAccessToken;
	public static String getGeneratedAccessToken() {
		return generatedAccessToken;
	}

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("https://api.hubapi.com/oauth/v1/token");	
		MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>();		
		params.add("grant_type", "refresh_token");
		params.add("client_id", "18a75f5b-4d02-4d04-849d-60bf4ed079eb");
		params.add("client_secret", "ad298ead-5a03-490b-9e49-d4e0e24cc65d");
		params.add("refresh_token", "18f2430e-b379-445c-b6e3-791dcf5bb154");

		try {
			generatedAccessToken = refreshAccessToken(client, webTarget, params);
			System.out.println("This is access token : "+generatedAccessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private static String refreshAccessToken (Client client, WebTarget webTarget, MultivaluedMap<String, String> params) throws Exception {
		String accessToken = "";

		Invocation.Builder invocationBuilder =  webTarget.request();
		//Response resp = invocationBuilder.accept(MediaType.APPLICATION_JSON).post(Entity.form(params));
		Response resp = invocationBuilder.accept(MediaType.APPLICATION_JSON).post(Entity.form(params));
				
		System.out.println(resp);
		try {
			if (resp.getStatus() == 200) {
				String json = resp.readEntity(String.class);
				System.out.println(json);
				JSONObject jsonObj = new JSONObject(json);
				accessToken = jsonObj.getString("access_token");
				int expiry = jsonObj.getInt("expires_in");
				int hours = expiry/3600;
				System.out.println("Access token expire time "+ hours+" Hours");
			}
			else {
				int sType = resp.getStatus();
				throw new Exception(sType + " : " + resp.getStatusInfo().getReasonPhrase());
			}
		}
		finally {
			resp.close();
		}
		return accessToken;
	}
}

