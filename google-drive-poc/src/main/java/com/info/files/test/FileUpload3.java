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
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class FileUpload3 {
	private static final String APPLICATION_NAME = "File Upload 3";
	private static HttpTransport httpTransport;
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory dataStore;
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStore = new FileDataStoreFactory(dataStoreDir);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String []args){
		InputStream iStream = FileUpload3.class.getResourceAsStream("client_secret_1.json");
		GoogleClientSecrets secret;
		GoogleAuthorizationCodeFlow flow;
		Credential credential;
		Drive drive;
		Files files;
		String folderId = null;
		String fileId = null;
		StringBuilder prevParent = null;
		try{
			secret = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY, secret, scopes).setDataStoreFactory(dataStore).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User");
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			files = drive.files();
			FileList list = files.list().setFields("parents").execute();
			List<File> fileList = list.getFiles();
			if(fileList == null || fileList.size() == 0){
				System.out.println("No Files");
			}
			else{
				for(File f : fileList){
					if(f.getName().equals("Untitled Document")){
						fileId = f.getId();
						prevParent = new StringBuilder();
						for(String parent : f.getParents()){
							prevParent = prevParent.append(parent);
							prevParent = prevParent.append(',');
						}
					}
					if(f.getName().equals("Sample Folder 1")){
						folderId = f.getId();
					}
				}
				if(fileId != null && folderId != null && prevParent != null){
					files.update(fileId, null).setAddParents(folderId).setRemoveParents(prevParent.toString()).setFields("id, parents").execute();
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
