package com.info.spreadsheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

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
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class SpreadsheetUpdate {

	private static final String APPLICATION_NAME ="GoogleSpreadsheet";
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	private static final JsonFactory JSON_FACTORY =JacksonFactory.getDefaultInstance();
	private static HttpTransport HTTP_TRANSPORT;
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);
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

	public static void main(String[] args) throws IOException
	{


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
			String spreadsheetId = "1aJ2Bpn7i1Zwm_s1CL4EriLynBNuYUmL4aL-IFXATX50";
			String range = "A1:B3";
			JSONArray jsonArray= new JSONArray();
			JSONArray jsonArray1= new JSONArray();
			JSONArray jsonArray2= new JSONArray();

			jsonArray1.put("Wheel1");
			jsonArray1.put("dggfg");
			jsonArray2.put("Wheel2");
			jsonArray2.put("dggfgeee");

			try {
				jsonArray.put(0, jsonArray1);
				jsonArray.put(1, jsonArray2);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			List<Object> list1 = new ArrayList<Object>();
			List<Object> list2 = new ArrayList<Object>();
			list1.add("Wheeul1");
			list1.add("dggfg");
			list2.add("Wheel2");
			list2.add("dggfgeee");

			List<List<Object>> valueList=new ArrayList<List<Object>>();
			valueList.add(list1);
			valueList.add(list2);
			String valueInputOption ="USER_ENTERED";      
			ValueRange requestBody = new ValueRange();
			requestBody.setRange(range);
			requestBody.setValues(valueList);
			Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, range, requestBody);
			request.setValueInputOption(valueInputOption);
			UpdateValuesResponse response = request.execute();
			System.out.println(response);
		}
	}
}
