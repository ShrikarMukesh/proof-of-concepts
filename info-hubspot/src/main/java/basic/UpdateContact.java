package basic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;

public class UpdateContact {
	
	public static void main(String[] args) throws JSONException {
		
		Client client = ClientBuilder.newClient().property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		WebTarget target = client.target("https://api.hubapi.com/crm/v3/objects/contacts/1");
		
		JSONObject jsonObj = new JSONObject();
		JSONObject prop = new JSONObject();
		prop.put("firstname", "Info-123");
		jsonObj.put("properties", prop);
		
		Response res = target.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer CNCHoZ6-LhIDAQEDGOmhsgMgmZ3dBCj34A0yGQCfqstrpZrU-r-oWbDWIho0zYVUTYgQFZM6GgAKAkEAAAyAwqcLAAAAAQAAAAAAAAAYwAAPQhkAn6rLay4vFvmNLhZdp4hPJJAHD5_efvzX")
		        .accept(MediaType.APPLICATION_JSON).method("PATCH", Entity.json(jsonObj.toString()));
		
		if (res.getStatus() == 200) {
			System.out.println(res.readEntity(String.class));
		}		
	}
}
