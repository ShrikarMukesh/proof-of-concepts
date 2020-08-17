package com.info.files.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.GeneratedIds;
import com.info.drive.credentials.DriveCredential;

public class IdGenerate {
	private static final String APPLICATION_NAME = "Id Generate";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1_2.json";
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Files files = drive.files();
		
		/*java.io.File fileToUpload = new java.io.File("C:\\Users\\InfometryPC\\Pictures\\Saved Pictures\\Pic2.jpg");
		FileContent fileContent;*/
		try{
			GeneratedIds gIds = files.generateIds().execute();
			List<String> ids = gIds.getIds();
			System.out.println(gIds.getKind());
			for(String id : ids){
				System.out.println(id);
				
			}
			
			/*File file = new File();
			file.setName(fileToUpload.getName());
			file.setId(ids.get(0));
			
			MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap();
			String mimeType = mimeTypeMap.getContentType(fileToUpload);
			fileContent = new FileContent(mimeType, fileToUpload);
			File temp = files.create(file, fileContent).execute();
			System.out.println(temp);*/
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
