package com.litmos.poc;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
class DataAdapter{
	private int start;
	private int limit;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
public class GetUser2 {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		DataAdapter dataAdapter = new DataAdapter();
		Client client ;
		String baseURI = "https://api.litmos.com/v1.svc";
		String apikey = "6E8A9336-ED24-4396-969A-D496EC0BEC3C";
		String source = "Infometry Inc";
		int start = dataAdapter.getStart();
		int limit = dataAdapter.getLimit();
		String getUsersURI = baseURI + "/users";

		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();							
		queryParams.add("apikey", apikey);
		queryParams.add("source", source);
		queryParams.add("sendmessage","false");
		queryParams.add("start", String.valueOf(start));
		queryParams.add("limit", String.valueOf(limit));

		Client restClient = Client.create();

		WebResource webResource = restClient.resource(getUsersURI);

		ClientResponse clientResponse = webResource.queryParams(queryParams)
				.queryParams(queryParams)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if(clientResponse != null){
			int status = clientResponse.getStatus();
			if(status == 200){
				String stringResponse = clientResponse.getEntity(String.class);
				System.out.println(stringResponse);
				try {
					JSONArray jsonArray = new JSONArray(stringResponse);
					if(jsonArray.length() > 0){
						dataAdapter.setStart(start + limit);
					}
					else{
						return ;
					}
				} catch (JSONException e) {
					return ;
				}
			}
			else{
				return ;
			}
		}

		/*Client restClient = Client.create();
		WebResource webResource = restClient.resource(getUsersURI);
		ClientResponse resp = webResource.accept("application/json")
				.get(ClientResponse.class);
		if(resp.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}
		String output = resp.getEntity(String.class);
		System.out.println("response: "+output);*/
	}
}
