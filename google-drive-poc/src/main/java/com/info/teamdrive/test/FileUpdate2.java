package com.info.teamdrive.test;

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
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;

public class FileUpdate2 {
	
	private static final String APPLICATION_NAME = "File Update 2";
	private static HttpTransport httpTransport;
	private static FileDataStoreFactory dataStoreFactory;
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static JsonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(dataStoreDir);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String []args){
		
		InputStream iStream = FileUpdate1.class.getResourceAsStream("Team_Drive1.json");
		GoogleClientSecrets secret;
		GoogleAuthorizationCodeFlow flow;
		Credential credential;
		
		try{
			secret = GoogleClientSecrets.load(jacksonFactory, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jacksonFactory, secret, scopes).setDataStoreFactory(dataStoreFactory).setAccessType("online").setApprovalPrompt("force").build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User1");
			Drive drive = new Drive.Builder(httpTransport, jacksonFactory, credential).setApplicationName(APPLICATION_NAME).build();
			Files files = drive.files();
			
			File file = files.get("1l14Bfdd06r5qa8VPcYM25Bbzkylt-U-XQCORpY4HMtk").execute();
			//file.setName("Anup");
			
			java.io.File fileContent = new java.io.File("Anup");
			FileContent mediaContent = new FileContent(null, fileContent);
			File updateFile = drive.files().update("1l14Bfdd06r5qa8VPcYM25Bbzkylt-U-XQCORpY4HMtk", file,mediaContent).execute();
			
			System.out.println(updateFile);
			
			
		}
		catch(IOException e){
			e.printStackTrace();			
		}
		
	}

}
