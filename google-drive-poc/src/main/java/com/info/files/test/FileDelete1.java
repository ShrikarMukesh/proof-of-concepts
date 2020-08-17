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
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class FileDelete1 {
	private static final String APPLICATION_NAME = "File Delete 1";
	private static HttpTransport httpTransport;
	private static java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static FileDataStoreFactory storeFactory;
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			storeFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static void main(String []args){
		InputStream inputStream = FileDelete1.class.getResourceAsStream("client_secret_1.json");
		GoogleClientSecrets secrets;
		GoogleAuthorizationCodeFlow flow;
		Credential credential;
		Drive drive;
		FileList fileList;
		
		try{
			secrets = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(inputStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY, secrets, scopes).setDataStoreFactory(storeFactory).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Test User 1");
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			fileList = drive.files().list().execute();
			List<com.google.api.services.drive.model.File> list = fileList.getFiles();
			if(list == null || list.size() == 0){
				System.out.println("No Files");
			}
			else{
				for(File file : list){
					//if(file.getName().equals("GetPostingTransactionSummaryResult.java")){
						String id = file.getId();
						drive.files().delete(id).execute();
					//}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
