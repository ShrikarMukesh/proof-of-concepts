package com.info.test;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.DriveScopes;
import com.info.drive.credentials.DriveCredential;

public class TokenTest1 {
	private static final String APPLICATION_NAME = "Token Test 1";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1.json";
	
	@SuppressWarnings("static-access")
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		System.out.println(credential.getAccessToken());
		System.out.println(credential.getRefreshToken());
		System.out.println(credential.getExpiresInSeconds()/60);
		
	}
}
