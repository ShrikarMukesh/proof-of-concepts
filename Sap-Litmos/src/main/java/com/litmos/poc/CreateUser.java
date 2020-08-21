package com.litmos.poc;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CreateUser {
	public static void main(String a[]){     
        String url = "https://api.litmos.com/v1.svc/users?apikey=6E8A9336-ED24-4396-969A-D496EC0BEC3C&source=Infometry Inc";
        String jsonInput = "";
           
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        
        ClientResponse resp = webResource.type("application/json")
                                    .post(ClientResponse.class, jsonInput);
        if(resp.getStatus() != 200){
            System.err.println("Unable to connect to the server");
        }
        String output = resp.getEntity(String.class);
        System.out.println("response: "+output);
    }
}
