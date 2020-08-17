package com.info;

import java.io.IOException;

import com.google.api.client.auth.oauth2.TokenResponse;

public class AccesToken {
	public static void main(String[] args) {
		
	}
	/*public TokenResponse refreshAccessToken(String refreshToken) throws IOException {
	    TokenResponse response = new GoogleRefreshTokenRequest
	    		(new NetHttpTransport(),new JacksonFactory(),refreshToken,"your clientId","your clientSecret").execute();
	    
	    System.out.println("Access token: " + response.getAccessToken());

	    return response;
	}*/
}
