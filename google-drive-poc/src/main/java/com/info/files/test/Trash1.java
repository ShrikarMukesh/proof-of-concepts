package com.info.files.test;

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
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;

public class Trash1 {

	private static final String APPLICATION_NAME = "Trash 1";
	private static HttpTransport httpTransport;
	private static JsonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory storeFactory;
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			storeFactory = new FileDataStoreFactory(dataStoreDir);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String []args){
		GoogleAuthorizationCodeFlow flow = AuthFlow1.getAuthorizationCodeFlow(httpTransport, jacksonFactory, storeFactory, scopes);
		Credential credential = AuthFlow1.getCredential(flow);
		Drive drive = new Drive.Builder(httpTransport, jacksonFactory, credential).setApplicationName(APPLICATION_NAME).build();
		Files driveFiles = drive.files();
		try{
			driveFiles.emptyTrash().execute();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}

class AuthFlow1{
	
	public static GoogleAuthorizationCodeFlow getAuthorizationCodeFlow(HttpTransport httpTransport, JsonFactory jacksonFactory, FileDataStoreFactory dataStore, List<String> scopes){
		GoogleAuthorizationCodeFlow flow = null;
		InputStream iStream = AuthFlow1.class.getResourceAsStream("client_secret_1.json");
		GoogleClientSecrets secret;
		
		try{
			secret = GoogleClientSecrets.load(jacksonFactory, new InputStreamReader(iStream));		
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jacksonFactory, secret, scopes).setDataStoreFactory(dataStore).build();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return flow;
	}
	
	public static Credential getCredential(GoogleAuthorizationCodeFlow flow){
		Credential credential = null;
		try{
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User 1");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return credential;
	}
	
}