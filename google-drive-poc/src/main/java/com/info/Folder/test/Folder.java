package com.info.Folder.test;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import com.google.api.services.drive.model.File;
import com.info.drive.credentials.DriveCredential;

public class Folder {
	private static final String APPLICATION_NAME = "Folder creation";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	
  public static void main(String[] args) {
	
	  
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, "client_secret_1_2.json");
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		
		try{
	         File fileMetadata = new File();
	         fileMetadata.setName("AnupFolder");
	         fileMetadata.setMimeType("application/vnd.google-apps.folder");

	       
			
			File file = drive.files().create(fileMetadata)
	        .setFields("id")
	        .execute();
	        System.out.println("Folder ID: " + file.getId());
		}catch(IOException e)
		{
			e.printStackTrace();
		}
}
}