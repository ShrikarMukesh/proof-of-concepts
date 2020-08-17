package com.info.about.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.Credential.AccessMethod;
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
import com.google.api.services.drive.Drive.About;
import com.google.api.services.drive.Drive.About.Get;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.User;

public class About1 {
	private static final String APPLICATION_NAME = "About 1";
	private static HttpTransport httpTransport; 
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory storeFactory;
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
		
		InputStream iStream = About1.class.getResourceAsStream("UnknownAccount.json");
		GoogleClientSecrets secret; 
		GoogleAuthorizationCodeFlow flow;
		Credential credential;
		Drive drive;
		
		try{
			secret = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY,secret, scopes).setDataStoreFactory(storeFactory).build();

			List<String> pathPart = flow.newAuthorizationUrl().getPathParts();
			for(String path : pathPart){
				System.out.println(path);
			}
			//System.out.println(flow.newAuthorizationUrl().getState());
		   //System.out.println(flow.newAuthorizationUrl());
			
		 	credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User1");
			AccessMethod method = credential.getMethod();
			System.out.println(method.toString());

			//System.out.println(credential.getAccessToken());
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

			About about = drive.about();
			Get get = about.get();
			String str = get.getFields();
			System.out.println(str+"============");

			com.google.api.services.drive.model.About abt = about.get().setFields("user").execute();
			User user = abt.getUser();
			System.out.println(user.getDisplayName());
			System.out.println(user.getEmailAddress());
			System.out.println(user);
			//StorageQuota quota = abt.getStorageQuota();
			//System.out.println(quota.size());
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
