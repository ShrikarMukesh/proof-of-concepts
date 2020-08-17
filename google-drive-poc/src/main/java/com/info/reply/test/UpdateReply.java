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

public class UpdateReply {

	private static final String APPLICATION_NAME = "Update Reply";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "./credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1.json";
	
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
				if(file.getName().equals("Pic2.jpg")){
					fileId = file.getId();
					break;
				}
			}
			
			if(fileId != null){
				List<Comment> commentsList = comments.list(fileId).setFields("comments").execute().getComments();
				String commentId = null;
				for(Comment comment : commentsList){
					if(comment.getContent().equals("Superb")){
						commentId = comment.getId();
						break;
					}
				}
				
				if(commentId != null){
					Reply reply = new Reply();
					reply.setContent("Yes Really Superb");
					
					List<Reply> repliesList = replies.list(fileId, commentId).setFields("replies").execute().getReplies();
					
					for(Reply temp : repliesList){
						if(temp.getContent().equals("Well Said")){
							Reply temp2 = replies.update(fileId, commentId, temp.getId(), reply).setFields("content").execute();
							System.out.println(temp2.getContent());
							break;
						}
					}					
					
				}
			}
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
}
