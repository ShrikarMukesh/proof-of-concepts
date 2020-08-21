package com.litmos.poc;

import java.io.FileInputStream;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CreateBulkImport {
	public static void main( String[] args ) throws JSONException, IOException {
		String baseURL = "https://apidev.litmos.com/v1.svc/bulkimports";
		String apikey = "f95a9ab3-5b8c-4b6d-b2b1-cb49852b2e20";
		String source = "InfometryDev";

		Client client = Client.create();
		WebResource resource = client.resource(baseURL);      

		/*File file = new File("D:\\GoogleDrive\\flatfile\\Files_List.csv");


		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		while ((st = br.readLine()) != null)

			System.out.println(st);*/

		@SuppressWarnings("deprecation")
		String json = IOUtils.toString(new FileInputStream("D:\\litmos_ff\\convertcsv1.json"));
		System.out.println(json);



		/*LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
		lineNumberReader.skip(Long.MAX_VALUE);
		int lines = lineNumberReader.getLineNumber();
		System.out.println(lines);

		int numberOfRequest= lines-1;*/
		int numberOfRequest=103;

		int i;

		ClientResponse clientResponse = null;

		while(true) {
			int start = 0;
			int limit = 10;

			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			queryParams.add("apikey", apikey);
			queryParams.add("source", source);
			queryParams.add("limit", String.valueOf(limit));

			resource = resource.queryParams(queryParams); 


			int quotient = numberOfRequest/limit;
			int remainder= numberOfRequest%limit;
			//	clientResponse = resource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,json);
			for(i=0;i<quotient;i++){
				System.out.println(i);
				start=start+limit;
				clientResponse = resource.queryParam("start",String.valueOf(start)).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,json.toString());
				System.out.println(clientResponse);

			}

			if(remainder>0) {
				start=start+limit;
				clientResponse = resource.queryParam("start",String.valueOf(start)).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,json.toString());
				System.out.println(start);
			}
			break;

		}

	}
}
