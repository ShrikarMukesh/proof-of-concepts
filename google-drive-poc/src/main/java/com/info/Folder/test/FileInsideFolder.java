package com.info.Folder.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.info.drive.credentials.DriveCredential;

public class FileInsideFolder {
	
	private static final String APPLICATION_NAME = "GoogleDriveConnectorTesting";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;

	public static void main(String[] args) throws IOException {

		Credential credential = DriveCredential.getCredential(scopes, credentialLoc, "UnknownAccount.json");
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();

		String folderId = "'1os6DgL99avksyuOGkPd1_vjxLXqqM6Bu'";
		List<File> result = new ArrayList<File>();
		System.out.println(result);
		Files.List request = drive.files().list();
		request.setQ(folderId+" in parents");

		FileList files = request.execute();
		System.out.println(files);
	}
}