package info.permission.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Permissions;

import com.info.drive.credentials.DriveCredential;

public class GetPermission {

	private static final String APPLICATION_NAME = "Get Permission";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "client_secret_1_2.json";
	
	public static void main(String []args){
		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Files files = drive.files();
		Permissions permissions = drive.permissions();
		
		try{
			List<File> filesList = files.list().execute().getFiles();
			String fileId = null;			
			
			for(File file : filesList){
				if(file.getName().equals("Pic7.jpg")){
					fileId = file.getId();
					System.out.println(fileId);
					break;
				}
			}
			
			if(fileId != null){
				List<Permission> permissionList = permissions.list(fileId).setFields("permissions").execute().getPermissions();
				for(Permission permission : permissionList){
					System.out.println(permission.getId());
					System.out.println(permission.getDisplayName());
					System.out.println(permission.getDomain());
					System.out.println(permission.getRole());
					System.out.println(permission.getType());
				}
				
			}
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
}
