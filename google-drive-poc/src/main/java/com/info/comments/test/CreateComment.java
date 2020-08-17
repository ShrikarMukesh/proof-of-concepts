package com.info.comments.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Comments;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.User;
import com.info.drive.credentials.DriveCredential;

public class CreateComment {
	
	private static final String APPLICATION_NAME = "Create Comment";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1_2.json";
	
	@SuppressWarnings("static-access")
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Files files = drive.files();
		Comments comments = drive.comments();
		try{
			List<File> list = files.list().execute().getFiles();
			String fId = null;
			for(File file : list){
				if(file.getName().equals("OAuth Test3")){
					fId = file.getId();
					break;
				}		
			}			
			String commentContent = "What is this????";
			Comment comment = new Comment();
			comment.setContent(commentContent);
			User user = new User();
			user.setEmailAddress("unknown.person786.ka3@gmail.com");
			user.setDisplayName("Unknown Person3");
			comment.setAuthor(user);
			comments.create(fId, comment).setFields("content").execute();		
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}	
}
