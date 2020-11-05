package nextReq;

import java.net.HttpURLConnection;
import java.net.URL;

public class ExportData {
	public static void main(String[] args) throws Exception {
		sendResponse();
	}
	public static String sendResponse() throws Exception {

		/*String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
				"<call callerName=\"Informatica AdaptiveInsights connector\" method=\"exportData\">\r\n" + 
				"    <credentials instanceCode=\"SINGULARITYUNIVERSITY\" locale=\"\" login=\"zoby.shaikh@su.org\" password=\"welcome\"/>\r\n" + 
				"    <version isDefault=\"false\" name=\"2020 COP\"/>\r\n" + 
				"    <format includeUnmappedItems=\"false\" useInternalCodes=\"true\"/>\r\n" + 
				"    <filters>\r\n" + 
				"        <levels>\r\n" + 
				"            <level includeDescendants=\"false\" isRollup=\"false\" name=\"SINGULARITY UNIVERSITY\"/>\r\n" + 
				"        </levels>\r\n" + 
				"        <timeSpan end=\"04/2020\" start=\"01/2020\"/>\r\n" + 
				"    </filters>\r\n" + 
				"</call>";*/

		String request =  ConstructRequest.exportSheetGuideWire();
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
		//System.out.println(response);
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
