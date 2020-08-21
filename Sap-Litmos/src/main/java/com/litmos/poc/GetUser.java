package com.litmos.poc;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class GetUser {

	int start;

	public int getStart() {
		return start;
	}

	public int setStart(int start) {
		return this.start = start;
	}

	public static void main(String a[]) throws JSONException{         


		ClientResponse resp;
		GetUser g = new GetUser();

		while(true) {
			String url = "https://api.litmos.com/v1.svc/users?apikey=6E8A9336-ED24-4396-969A-D496EC0BEC3C&source=MY-APP";
			//String apikey="";
			int start=g.getStart();
			int limit =1000;
			MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
			queryParams.add("start", String.valueOf(start));
			queryParams.add("limit",String.valueOf(limit));
			int count=0;
			Client restClient = Client.create();
			WebResource webResource = restClient.resource(url);

			resp = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			String s  =resp.getEntity(String.class);
			JSONArray array = new JSONArray(s);

			if(array.length() > 0) {
				for(int i=0;i<array.length();i++) {
					resp = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
					s  = resp.getEntity(String.class);

					start =g.setStart(start+limit);
					if(resp.getStatus() != 200){
						System.err.println("Unable to connect to the server");
					}
					count=count+1;
					// String output = resp.getEntity(String.class);
					System.out.println("response: "+s);
					System.out.println("count :"+count);
				}
			}else {
				break;
			}
		}
	}
}


