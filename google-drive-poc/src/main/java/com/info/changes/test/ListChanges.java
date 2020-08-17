package com.info.changes.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Change;
import com.info.drive.credentials.DriveCredential;
import com.google.api.services.drive.Drive.Changes;
import com.google.api.services.drive.Drive.Changes.GetStartPageToken;


public class ListChanges {

	private static final String APPLICATION_NAME = "List Changes";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "./credentials/" + APPLICATION_NAME;
	private static String clientSecret = "NewCredjson";
	
	@SuppressWarnings("static-access")
	public static void main(String []args){
		
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		
		Changes changes = drive.changes();
		
		try{
			
			GetStartPageToken token = changes.getStartPageToken();
			String startToken = token.execute().getStartPageToken();
			System.out.println(startToken);
			
			List<Change> changesList = changes.list(startToken).execute().getChanges();
			System.out.println(changesList);
			for(Change change : changesList){
				System.out.println(change.getFile().getName());
				System.out.println(change.getRemoved());
			}
						
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
