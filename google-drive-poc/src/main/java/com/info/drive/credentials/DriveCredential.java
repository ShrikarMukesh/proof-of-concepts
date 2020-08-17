package com.info.drive.credentials;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

public class DriveCredential {
	private static HttpTransport httpTransport;
	private static JsonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

	private static final DriveCredential OBJ = new DriveCredential();

	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
		}
	}

	private DriveCredential(){}

	public static DriveCredential getDriveCredentialObject(){
		return OBJ;
	}

	public static GoogleAuthorizationCodeFlow getGoogleAuthorizationCodeFlow (List<String> scopes, String credentialLoc, String clientSecret) {
		java.io.File clientSecretFile = new java.io.File(credentialLoc);
		FileDataStoreFactory dataStore = null;

		GoogleClientSecrets secrets = null;
		InputStream iStream = DriveCredential.class.getResourceAsStream(clientSecret);

		//System.out.println(iStream);
		//InputStreamReader reader = new InputStreamReader(iStream);

		GoogleAuthorizationCodeFlow flow = null;

		try{
			dataStore = new FileDataStoreFactory(clientSecretFile);
			secrets = GoogleClientSecrets.load(jacksonFactory, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jacksonFactory, secrets, scopes).setDataStoreFactory(dataStore).build();

		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(iStream != null){
				try{
					iStream.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}

		return flow;
	}

	public static Credential getCredential(List<String> scopes, String credentialLoc, String clientSecret){
		Credential credential = null;
		try{
			credential = new AuthorizationCodeInstalledApp(getGoogleAuthorizationCodeFlow(scopes, credentialLoc, clientSecret), new LocalServerReceiver()).authorize("User 1");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return credential;
	}
}
