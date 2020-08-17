package com.info.files.test;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

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
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

public class OAuthTest1 {
	
	private final static String APPLICATION_NAME = "OAuth Test";
	private final static File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/"+APPLICATION_NAME);
	private final static JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static FileDataStoreFactory dataStoreFactory;
	private static HttpTransport httpTransport;
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
		try{
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String []args){
		InputStream iStream = OAuthTest1.class.getResourceAsStream("client_secret_1_2.json");
		GoogleClientSecrets secrets = null;
		GoogleAuthorizationCodeFlow flow = null;
		Credential credential = null;
		Drive drive = null;
		FileList list = null;
		
		try{
			secrets = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY, secrets, SCOPES)
					 	.setAccessType("offline")
					 	.setApprovalPrompt("auto")
					 	.setDataStoreFactory(dataStoreFactory)
					 	.build();
			
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("New User");
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			list = drive.files().list().execute();
			System.out.println(list.size() + "\t" + list);
			List<com.google.api.services.drive.model.File> files = list.getFiles();
			if(files == null || files.size() == 0){
				System.out.println("No Files");
			}
			else{
				System.out.println("Files : ");
				System.out.println(files);
				for(com.google.api.services.drive.model.File file : files){
					System.out.println(file.getName() + "\t" + file.getId());
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		 
		 
	}
}
