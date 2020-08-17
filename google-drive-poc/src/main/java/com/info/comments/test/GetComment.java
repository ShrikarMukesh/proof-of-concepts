package com.info.comments.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Comments;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.Comment;

import com.google.api.services.drive.model.File;

import com.google.api.services.drive.DriveScopes;
import com.info.drive.credentials.DriveCredential;

public class GetComment {

	private static final String APPLICATION_NAME = "Get Comment";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1_2.json";
		
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Comments comments = drive.comments();
		Files files = drive.files();
		try{
			List<File> list = files.list().execute().getFiles();
			String fId = null;
		
			for(File file : list){
				if(file.getName().equals("Untitled document")){
					fId = file.getId();
					System.out.println(fId);
					break;
				}
			}
			List<Comment> cList = comments.list(fId).setFields("comments").execute().getComments();
			System.out.println(cList);
			for(Comment comment : cList){
				System.out.println(comment.getContent());
				System.out.println(comment.getId());
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
}
