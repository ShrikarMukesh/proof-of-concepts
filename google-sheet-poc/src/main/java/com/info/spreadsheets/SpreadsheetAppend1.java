package com.info.spreadsheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
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
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Append;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class SpreadsheetAppend1 {

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
	public static void main(String args[]) throws IOException, GeneralSecurityException {
		InputStream iStream = SpreadsheetRead.class.getResourceAsStream("clientsecret.json");
		GoogleClientSecrets secrets;
		GoogleAuthorizationCodeFlow flow;
		Credential credential;

		{
			secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(iStream));
			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("Temp User 1");

			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,credential).setApplicationName(APPLICATION_NAME)
					.build();
			// The ID of the spreadsheet to update.
			String spreadsheetId = "1c9KXcDeFFsVaZ0sUPE7qagdsVTAiTgzXTSwt93nwDoo"; // TODO: Update placeholder value.

			// The A1 notation of a range to search for a logical table of data.
			// Values will be appended after the last row of the table.
			String range = "A1:B3"; // TODO: Update placeholder value.

			// How the input data should be interpreted.
			String valueInputOption = "USER_ENTERED"; // TODO: Update placeholder value.

			// How the input data should be inserted.
			String insertDataOption = "INSERT_ROWS"; // TODO: Update placeholder value.

			// TODO: Assign values to desired fields of `requestBody`:
			List<Object> list1 = new ArrayList<Object>();
			List<Object> list2 = new ArrayList<Object>();
			list1.add("Yogesh");
			list1.add("Prasad");
			list2.add("Pratap");
			list2.add("Gaurav");
			List<List<Object>> valueList=new ArrayList<List<Object>>();
			valueList.add(list1);
			valueList.add(list2);
			ValueRange requestBody = new ValueRange();
			requestBody.setRange(range);
			requestBody.setValues(valueList);

			Sheets sheetsService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName("GoogleSpreadsheet/0.1")
					.build();
			//


			Append request1 =
					sheetsService.spreadsheets().values().append(spreadsheetId, range, requestBody);

			request1.setValueInputOption(valueInputOption);
			request1.setInsertDataOption(insertDataOption);

			AppendValuesResponse response = request1.execute();

			// TODO: Change code below to process the `response` object:
			System.out.println(response);
		}

	}
}