package com.info.spreadsheets;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpreadsheetDelete {
	private static final String APPLICATION_NAME ="GoogleSpreadsheet1";
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
	 System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	 private static FileDataStoreFactory DATA_STORE_FACTORY;
	 private static final JsonFactory JSON_FACTORY =JacksonFactory.getDefaultInstance();
	 private static HttpTransport HTTP_TRANSPORT;
	private static final List<String> SCOPES =
	        Arrays.asList(SheetsScopes.SPREADSHEETS);
	static{
	
	try {
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		
	} catch (GeneralSecurityException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}	

	    static {
	        try {
	            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
	        } catch (Throwable t) {
	            t.printStackTrace();
	            System.exit(1);
	        }
	    }
  public static void main(String args[]) throws IOException, GeneralSecurityException 
  {

	  InputStream iStream = SpreadsheetRead.class.getResourceAsStream("clientsecret.json");
 		GoogleClientSecrets secrets;
 		GoogleAuthorizationCodeFlow flow;
 		Credential credential;
 		secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(iStream));
 		
			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User 1");
			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			Files files = drive.files();
    
    
    String spreadsheetId = "1ziEMhDG45YWN1Yq321U7w2YeL0IJD4zGS5tEvFdNgWk";
    
    String valueInputOption = "InfometrySheet";
   
    List<ValueRange> data = new ArrayList<>(); 
    
    BatchUpdateValuesRequest requestBody = new BatchUpdateValuesRequest();
    
      requestBody.setValueInputOption(valueInputOption);
      requestBody.setData(data);
 
     ValueRange valuerange= new ValueRange();
     
     // requestBody.setProperties(new SpreadsheetProperties().setTitle("InfometrySheet"));
    
      Sheets sheetsService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName("GoogleSpreadsheet/0.1")
            .build();
    //  Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);
    //
    
    Sheets.Spreadsheets.Values.BatchUpdate request =
        sheetsService.spreadsheets().values().batchUpdate(spreadsheetId, requestBody);

    BatchUpdateValuesResponse response = request.execute();

   
    System.out.println(response);
  }


}