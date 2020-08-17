package com.info.channel.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Changes;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
//import com.google.api.services.drive.model.Channel;
import com.info.drive.credentials.DriveCredential;

public class Channel {
	private static final String APPLICATION_NAME = "Folder creation";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String clientSecret = "client_secret_1_2.json";
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
	
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc,clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();

		// Channel channel = new Channel();
		Changes changes = drive.changes();
		Files files = drive.files();
		List<File> fileList;
		try{
			fileList = files.list().execute().getFiles();
			System.out.println(files.list().getPageToken());

			for(File file : fileList){
				if(file.getName().equals("GreytHR.xlsx")){
					com.google.api.services.drive.model.Channel channel = new com.google.api.services.drive.model.Channel();
					channel.setId(APPLICATION_NAME + Math.random());
					System.out.println(APPLICATION_NAME + Math.random());
					channel.setResourceId(file.getId());
					drive.channels().stop(channel).execute();
					//channel.stop(changes.getStartPageToken().toString(), channel);
					System.out.println(changes.getStartPageToken().toString());
					System.out.println(file.getId());

					break;
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}