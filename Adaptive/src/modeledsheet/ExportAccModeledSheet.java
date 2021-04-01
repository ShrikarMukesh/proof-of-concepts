package modeledsheet;

import java.net.HttpURLConnection;
import java.net.URL;

public class ExportAccModeledSheet {
	public static void main(String[] args) throws Exception {
		sendResponse();
	}
	@SuppressWarnings("unused")
	public static String sendResponse() throws Exception {
        
		String request1 = "<?xml version='1.0' encoding='UTF-8'?>\r\n" + 
				"<call method=\"exportAccounts\" callerName=\"Informatica cloud\">\r\n" + 
				"    <credentials login=\"zoby.shaikh@su.org\" password=\"welcome\"/>\r\n" + 
				"    <include versionName=\"2020 COP\"/>\r\n" + 
				"    <sheet id=\"321\" />\r\n" + 
				"</call>\r\n" + 
				"";
		
       String request = "<?xml version='1.0' encoding='UTF-8'?>\r\n" + 
       		"<call method=\"exportAccounts\" callerName=\"Informatica cloud\">\r\n" + 
       		"    <credentials login=\"zoby.shaikh@su.org\" password=\"welcome\"/>\r\n" + 
       		"    <include versionName=\"July'19 Fcst v2\"/>\r\n" + 
       		"    <sheet id=\"1\" />\r\n" + 
       		"</call>\r\n" + 
       		"";
		// Make a URL connection to the Adaptive Planning web service URL
		URL url = new URL("https://api.adaptiveinsights.com/api/v18");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// Set the content type, the HTTP method, and tell the connection we expect output
		conn.setRequestProperty("content-type", "text/xml;charset=UTF-8");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		// Send the request
		writeRequest(conn, request);

		// Read the response
		String response = readResponse(conn);
		System.out.println(response);
		return response;
	}
	private static void writeRequest(HttpURLConnection conn, String request) throws Exception {
		conn.getOutputStream().write(request.getBytes("UTF-8"));
	}

	private static String readResponse(HttpURLConnection conn) throws Exception {
		byte[] buffer = new byte[4096];
		StringBuilder sb = new StringBuilder();
		int amt;
		while ((amt = conn.getInputStream().read(buffer)) != -1) {
			String s = new String(buffer, 0, amt, "UTF-8");
			sb.append(s);
		}
		return sb.toString();
	}
}
