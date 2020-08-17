package com.info.teamdrive.test;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Teamdrives;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.TeamDrive;
import com.google.api.services.drive.model.TeamDriveList;
import com.info.drive.credentials.DriveCredential;

public class TeamDrive_List {
	private static final String APPLICATION_NAME = "Google_Drive_REST_API_POC";
	private static String dataStoreDir = System.getProperty("user.home") + "/.credentials/" + APPLICATION_NAME;
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	private static HttpTransport httpTransport;
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();

	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String []args) throws IOException{

		Credential credential = DriveCredential.getCredential(scopes, dataStoreDir, "Team_Drive1.json");
		Drive drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
		Teamdrives teamDrives = drive.teamdrives();
		com.google.api.services.drive.Drive.Teamdrives.List teamDriveList = teamDrives.list();
		TeamDriveList listModel = teamDriveList.execute();
		List<TeamDrive> list = listModel.getTeamDrives();
		for(TeamDrive teamDrive : list){
			System.out.println(teamDrive.getId() + " : " + teamDrive.getName());	
		}
		
	}
}
