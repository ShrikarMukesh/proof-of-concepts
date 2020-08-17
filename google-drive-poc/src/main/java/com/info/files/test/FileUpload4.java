package com.info.files.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Create;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.GeneratedIds;

public class FileUpload4 {

	private static final String APPLICATION_NAME = "File Upload 4";
	private static HttpTransport httpTransport;
	private static java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory dataStore;
	private static final JsonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = Arrays.asList(DriveScopes.DRIVE);
	
	static{
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStore = new FileDataStoreFactory(dataStoreDir);
		}
		catch(IOException | GeneralSecurityException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String []args){
		InputStream iStream = FileUpload3.class.getResourceAsStream("client_secret_1.json");
		GoogleClientSecrets secret;
		GoogleAuthorizationCodeFlow flow;
		Credential credential;
		Drive drive=null;
		Files files=null;
		File file=null;
		String fileID=null;
		java.io.File mediaFile = null;
		InputStreamContent mediaContent = null;
		File fileMetadata = null;
		
		try{
			secret = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JACKSON_FACTORY, secret, scopes).setDataStoreFactory(dataStore).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User");
			drive = new Drive.Builder(httpTransport, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			files = drive.files();
			/*FileList list = files.list().setFields("parents").execute();
			List<File> fileList = list.getFiles();*/
			
			class CustomProgressListener implements MediaHttpUploaderProgressListener {
				  public void progressChanged(MediaHttpUploader uploader) throws IOException {
				    switch (uploader.getUploadState()) {
				      case INITIATION_STARTED:
				        System.out.println("Initiation has started!");
				        System.out.println(uploader.getProgress());
				        HttpHeaders headers = uploader.getInitiationHeaders();
				        System.out.println(headers);
				        System.out.println(headers.getContentRange());
				        System.out.println(headers.getLocation());
				        break;
				      case INITIATION_COMPLETE:
				        System.out.println("Initiation is complete!");
				        System.out.println(uploader.getProgress());
				        HttpHeaders headers1 = uploader.getInitiationHeaders();
				        System.out.println(headers1);
				        System.out.println(headers1.getContentRange());
				        System.out.println(headers1.getLocation());
				        break;
				      case MEDIA_IN_PROGRESS:
				        System.out.println(uploader.getProgress());
				        HttpHeaders headers2 = uploader.getInitiationHeaders();
				        System.out.println(headers2);
				        System.out.println(headers2.getContentRange());
				        System.out.println(headers2.getLocation());
				        
				        break;
				      case MEDIA_COMPLETE:
				        System.out.println("Upload is complete!");
				        break;
				      case NOT_STARTED:
				    	System.out.println("Not Started");
				    	break;
				    }
				  }
				}

				mediaFile = new java.io.File("C:\\Users\\infometry21\\Downloads\\CallidusCloudCommissions.zip");
				mediaContent =
				    new InputStreamContent("application/zip",
				        new BufferedInputStream(new FileInputStream(mediaFile)));
				mediaContent.setLength(mediaFile.length());
				fileMetadata = new File();
				fileMetadata.setName(mediaFile.getName());
				GeneratedIds gIds = files.generateIds().execute();
				List<String> ids = gIds.getIds();
				fileID=ids.get(0);
				fileMetadata.setId(fileID);
				Create req = files.create(fileMetadata, mediaContent);
				//req.set("uploadType", "resumable");
				HttpHeaders httpHeaders= new HttpHeaders();
				httpHeaders.set("X-Upload-Content-Type", "application/pdf");
				req.setRequestHeaders(httpHeaders);
				req.getMediaHttpUploader().setProgressListener(new CustomProgressListener());
				file=req.execute();
				//System.out.println(res.getHeaders());				
			
		}
		catch(IOException e){
			try {
				System.out.println("In Exception");
				files.update(fileID,fileMetadata, mediaContent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
}
