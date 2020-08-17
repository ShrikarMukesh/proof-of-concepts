package com.info.reply.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Reply;
import com.google.api.services.drive.Drive.Comments;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Replies;
import com.info.drive.credentials.DriveCredential;

public class GetReply {

	private static final String APPLICATION_NAME = "Get Reply";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1_2.json";
	
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Files files = drive.files();
		Replies replies = drive.replies();
		Comments comments = drive.comments();
		
		try{
			List<File> filesList = files.list().execute().getFiles();
			String fileId = null;			
			
			for(File file : filesList){
				if(file.getName().equals("Untitled document")){
					fileId = file.getId();
					System.out.println("File Id : " + fileId);
					break;
				}
			}
			
			if(fileId != null){
				List<Comment> commentsList = comments.list(fileId).setFields("comments").execute().getComments();
				String commentId = null;
				for(Comment comment : commentsList){
					if(comment.getContent().equals("Hello")){
						commentId = comment.getId();
						System.out.println("Comment Id : " + commentId);
						break;
					}
				}
				
				if(commentId != null){
					List<Reply> repliesList = replies.list(fileId, commentId).setFields("replies").execute().getReplies();
					System.out.println(repliesList);
					for(Reply reply : repliesList){
						System.out.println(reply.getContent());
						System.out.println("Reply Id : " + reply.getId());
					}
				}
			}
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
}
