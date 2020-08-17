package com.info.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

public class WebAppDriveTest {

	private static final String APPLICATION_NAME = "Web App Drive Test";
	private static String clientSecret = "client_secret_w_1.json";
	private static String redirectURI = "urn:ietf:wg:oauth:2.0:oob";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);

	public static void main(String []args){

		WebAppTest1 t1;

	}

}

class WebAppTest1{

	private static HttpTransport httpTransport ;
	private static JsonFactory jacksonFactory = JacksonFactory.getDefaultInstance();


	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
		}
	}

	public GoogleAuthorizationCodeFlow getFlow(HttpTransport httpTransport, JsonFactory jacksonFactory, String clientId, String clientSecret, List<String> scopes){
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jacksonFactory, "1078560796454-b85fv47t3fgugcmbt7q6c3t922vmeqoi.apps.googleusercontent.com", "RgsXy5iVISujWsA83pYLd9QM", scopes)
				.setAccessType("offline")
				.setApprovalPrompt("auto")
				.build();
		return flow;
	}

	public GoogleTokenResponse getToken(HttpTransport httpTransport, JsonFactory jacksonFactory, String clientId, String clientSecret, List<String> scopes){

		return null;

	}

	public Credential getCredential(String clientSecret, HttpTransport httpTransport, JsonFactory jacksonFactory, List<String> scopes){

		GoogleCredential credential = null;
		GoogleCredential credential1 = null;
		GoogleClientSecrets secret = null;
		try{
			credential = GoogleCredential.fromStream(new FileInputStream(clientSecret), httpTransport, jacksonFactory);
			secret = GoogleClientSecrets.load(jacksonFactory, new InputStreamReader(new FileInputStream(clientSecret)));
			credential1 = new GoogleCredential.Builder().setClientSecrets(secret)
					.setJsonFactory(jacksonFactory)
					.setTransport(httpTransport)
					.build();
			credential1.createScoped(scopes);

		}
		catch(IOException e){
			e.printStackTrace();
		}
		return credential1;
	}

}