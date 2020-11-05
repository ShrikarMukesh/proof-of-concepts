package accounts;

import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ExportAccountReq {
	
	public static void main(String[] args) throws Exception {
		
		String exportAccountRequest = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element callElement = doc.createElement("call");
			callElement.setAttribute("method", "exportAccounts");
			callElement.setAttribute("callerName", "Informatica");
			doc.appendChild(callElement);

			Element credentials = doc.createElement("credentials");
			credentials.setAttribute("login", "zoby.shaikh@su.org");
			credentials.setAttribute("password", "welcome");
			credentials.setAttribute("instanceCode","SINGULARITYUNIVERSITY");
			callElement.appendChild(credentials);

			Element version = doc.createElement("include");
			version.setAttribute("versionName", "2020 COP");
			callElement.appendChild(version);
			
			Element sheet = doc.createElement("sheet");
			sheet.setAttribute("id", "3");
			callElement.appendChild(sheet);
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();

			transformer.transform(new DOMSource(doc), new StreamResult(writer));

			// Get the String value of final xml document
			exportAccountRequest = writer.getBuffer().toString();
			System.out.println(exportAccountRequest);
			
		}catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
		String request = exportAccountRequest;


		URL url = new URL("https://api.adaptiveinsights.com/api/v18");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestProperty("content-type", "text/xml;charset=UTF-8");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);	
		writeRequest(conn, request);
		String response = readResponse(conn);
		System.out.println(response);
		//return response;
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
