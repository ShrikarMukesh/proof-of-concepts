package com.info.files.test;

import java.io.File;
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
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;

public class FileUpload1 {
	private static HttpTransport httpTransport;
	private static final String APPLICATION_NAME = "File Upload 1";
	private static FileDataStoreFactory dsFactory;
	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE); 
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dsFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static void main(String []args){
		InputStream is = FileUpload1.class.getResourceAsStream("File.json");
		GoogleClientSecrets secrets;
		GoogleAuthorizationCodeFlow flow;
		Drive drive;
		Credential credential;
		Files files;
		
		try{
			secrets = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(is));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY, secrets, SCOPES).setDataStoreFactory(dsFactory).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("TEST USER 3");
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			files = drive.files();
			com.google.api.services.drive.model.File file = new com.google.api.services.drive.model.File();
			file = file.setName("OAuth Test3");
			File f = new File(FileUpload1.class.getResource("D:\\flatfiles\\credentials.json").getPath());
			FileContent content = new FileContent("application/json", f);
			
			com.google.api.services.drive.model.File f1 = files.create(file, content).execute();
			System.out.println(f1);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
