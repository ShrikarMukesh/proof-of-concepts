package com.info.revisions.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.File.Capabilities;
import com.google.api.services.drive.model.Revision;
import com.google.api.services.drive.model.User;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Revisions;
import com.google.api.services.drive.Drive.Revisions.Get;
import com.info.drive.credentials.DriveCredential;

public class GetRevision {

	private static final String APPLICATION_NAME = "Get Revision";
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static String credentialLoc = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static String clientSecret = "Team_Drive1.json";

	@SuppressWarnings({ "unused", "static-access" })
	public static void main(String []args){

		Credential credential = DriveCredential.getDriveCredentialObject().getCredential(scopes, credentialLoc, clientSecret);
		Drive drive = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName(APPLICATION_NAME).build();
		Revisions revisions = drive.revisions();
		Files files = drive.files();
		Capabilities cap = null;
		String fileName=null;
		try{
			List<File> list = files.list().execute().getFiles();
			String fileId = null;
			for(File file : list){
				fileName = file.getName();
				if(fileName.equalsIgnoreCase("GreytHR.xlsx")){
					fileId = file.getId();
					System.out.println(fileId);
					System.out.println(fileName);
					//cap = file.getCapabilities();
					//System.out.println(cap.getCanReadRevisions());
					break;
				}
			}


			List<Revision> revisionList = revisions.list(fileId).execute().getRevisions();
			System.out.println(revisionList);

			for(Revision revision : revisionList){
				/*JsonFactory jsonFactory = revision.getFactory();
				String string = new String("lastModifyingUser");
				JsonParser parser = jsonFactory.createJsonParser(string);

				System.out.println(parser.getCurrentName());*/
				User user = revision.getLastModifyingUser();
				System.out.println(user);
				String id = revision.getId();
				System.out.println("revision id"+id);
				//System.out.println(user.getDisplayName());


			}
			Get revision= revisions.get("0B4MM8Zr4yD5xc05neHVFOGpaWXM", "0B4MM8Zr4yD5xc01URnd6Mi9UN1ZjaDA3TmJjQnBnVTd3VFlNPQ");
			FileOutputStream fos = new FileOutputStream("C:\\fileDownloard\\" + fileName);
			revision.setAlt("media");
			revision.executeAndDownloadTo(fos);
		}
		catch(IOException e){
			e.printStackTrace();
		}


	}

}
