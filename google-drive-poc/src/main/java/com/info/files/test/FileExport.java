package com.info.files.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.DriveScopes;
import com.info.drive.credentials.DriveCredential;

public class FileExport {
	private static final String APPLICATION_NAME = "File Export";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	
	public static void main(String []args){
		
		FileOutputStream fos = null; 
		@SuppressWarnings("static-access")
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, "woolworths.json");
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Files files = drive.files();
		try{
			
			FileList list = files.list().execute();
			List<File> fileList = list.getFiles();
			for(File file : fileList){
				if(file.getName().equals("ShrikarDetails.txt")){
					fos = new FileOutputStream("D:\\Download" + file.getName());
					files.export(file.getId(), "application/pdf").executeMediaAndDownloadTo(fos);
					//files.export(file.getId(), "application/pdf").execute();
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(fos != null){
				try{
					fos.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
