package com.info.changes.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Changes;
import com.google.api.services.drive.Drive.Changes.GetStartPageToken;
import com.google.api.services.drive.DriveScopes;

public class Changes1 {
	
	private static final String APPLICATION_NAME = "Changes 1";
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory dataStore;
	private static HttpTransport httpTransport;
	private static JsonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
		
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStore = new FileDataStoreFactory(dataStoreDir);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String []args){
		GoogleAuthorizationCodeFlow flow = AuthFlow.getFlow(httpTransport, jacksonFactory, scopes, dataStore);
		if(flow != null){
			Credential credential = AuthFlow.getCredentials(flow);
			Drive drive = new Drive.Builder(httpTransport, jacksonFactory, credential).setApplicationName(APPLICATION_NAME).build();
			Changes changes = drive.changes();
			try {
				GetStartPageToken token = changes.getStartPageToken();
				System.out.println(token);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}

class AuthFlow{
	
	private static GoogleAuthorizationCodeFlow flow;
	private static GoogleClientSecrets secret;
	private static Credential credential;
	
	public static GoogleAuthorizationCodeFlow getFlow(HttpTransport httpTransport, JsonFactory jacksonFactory, List<String> scopes, FileDataStoreFactory storeFactory){
		InputStream stream = AuthFlow.class.getResourceAsStream("client_secret_1.json");
		try{
			secret = GoogleClientSecrets.load(jacksonFactory, new InputStreamReader(stream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jacksonFactory, secret, scopes).setDataStoreFactory(storeFactory).build();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return flow;
		
	}
	
	public static Credential getCredentials(GoogleAuthorizationCodeFlow flow){
		try{
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User 1");
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return credential;
	}
}
