package com.info;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.Request;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class SheetsExample {
	public static void main(String args[]) throws IOException, GeneralSecurityException {

		String spreadsheetId = "1JesPHzDwLxgeyKANxbeqwwgB_rulHCfFTc4ExLs9chQ"; 
		
		List<Request> requests = new ArrayList<>(); 

		BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
		requestBody.setRequests(requests);

		Sheets sheetsService = createSheetsService();
		Sheets.Spreadsheets.BatchUpdate request =
				sheetsService.spreadsheets().batchUpdate(spreadsheetId, requestBody);

		BatchUpdateSpreadsheetResponse response = request.execute();
		System.out.println(response);
	}

	public static Sheets createSheetsService() throws IOException, GeneralSecurityException {
		
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		GoogleCredential credential = null;

		return new Sheets.Builder(httpTransport, jsonFactory, credential)
				.setApplicationName("Google-SheetsSample/0.1")
				.build();
	}
}