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
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.DriveScopes;

public class FolderCreate1 {
	private static final String APPLICATION_NAME = "Folder Create 1";
	private static HttpTransport httpTransport;
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory storeFactory;
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			storeFactory = new FileDataStoreFactory(dataStoreDir);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String []args){
		InputStream iStream = FolderCreate1.class.getResourceAsStream("client_secret_1.json");
		GoogleClientSecrets secret;
		GoogleAuthorizationCodeFlow flow;
		Credential credential;
		Drive drive;
		Files files;
		File file;
		
		try{
			secret = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY, secret, scopes).setDataStoreFactory(storeFactory).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User");
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			files= drive.files();
			file = new File();
			file.setName("Sample Folder 1");
			file.setMimeType("application/vnd.google-apps.folder");
			files.create(file).setFields("id").execute();
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
