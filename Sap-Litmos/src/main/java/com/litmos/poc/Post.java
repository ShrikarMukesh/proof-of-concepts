package com.litmos.poc;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
class Post {
	public static void main(String[] args) {

		Client client = Client.create();
		JSONObject obj = new JSONObject();
		try {
			
			obj.put("Id","um0OTczQ7Z81");
			obj.put("UserName","anand@infometry.net");
			obj.put("FirstName","anand");
			obj.put("LastName","M");
			obj.put("Active","true");
			obj.put("Email","anand@infometry.net");
			obj.put("AccessLevel","Learner");
			obj.put("Brand","Default");
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
		String jsonData = client.resource("https://api.litmos.com/v1.svc/users?apikey=6E8A9336-ED24-4396-969A-D496EC0BEC3C&source=Infometry")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,obj.toString())
				.getEntity(String.class);;
				System.out.println(jsonData);

	}

}
