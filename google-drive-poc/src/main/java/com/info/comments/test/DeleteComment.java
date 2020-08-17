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
import com.info.drive.credentials.DriveCredential;

public class DeleteComment {
	
	private static final String APPLICATION_NAME = "Delete Comment";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1.json";
	
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Files files = drive.files();
		Comments comments = drive.comments();
		
		try{
			List<File> list = files.list().execute().getFiles();
			String fileId = null;
			for(File file : list){
				if(file.getName().equals("Pic2.jpg")){
					fileId = file.getId();
					break;
				}
			}
			if(fileId != null){
				List<Comment> commentsList = comments.list(fileId).setFields("comments").execute().getComments();
				for(Comment comment : commentsList){
					if(comment.getContent().equals("Nice Pic")){
						comments.delete(fileId, comment.getId()).setFields("content").execute();
						break;
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
