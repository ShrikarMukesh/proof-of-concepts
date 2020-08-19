package com.info.spreadsheets;

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
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.SheetProperties;

public class SpreadsheetCopyTo {

	private static final String APPLICATION_NAME ="GoogleSpreadsheet";
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	private static final JsonFactory JSON_FACTORY =JacksonFactory.getDefaultInstance();
	private static HttpTransport HTTP_TRANSPORT;
	private static final List<String> SCOPES =
			Arrays.asList(SheetsScopes.SPREADSHEETS);
	static
	{	
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);

		} 
		catch (GeneralSecurityException e) 
		{
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

		String spreadsheetId = "1q0Wmp9A25oXUTtMB16vIfbztFkR5h_HC8SfU6bei3a0"; 


		int sheetId = 1359233342; 


		String destinationSpreadsheetId = "1QeWvRET98_83K7OForptgBAxnG4XJTlUtyjXP-pOd2w"; 



		CopySheetToAnotherSpreadsheetRequest requestBody = new CopySheetToAnotherSpreadsheetRequest();
		requestBody.setDestinationSpreadsheetId(destinationSpreadsheetId);

		Sheets sheetsService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName("GoogleSpreadsheet/0.1")
				.build();
		Sheets.Spreadsheets.SheetsOperations.CopyTo request =
				sheetsService.spreadsheets().sheets().copyTo(spreadsheetId, sheetId, requestBody);

		SheetProperties response = request.execute();


		System.out.println(response);
	}
}