package nextReq;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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

public class ConstructRequest {
	public static void main(String[] args) throws Exception {
		exportSheetGuideWire();

	}
	@SuppressWarnings("unused")
	public static String exportSheetGuideWire() throws Exception{

		//String[] arrayDimensions = {"Workday Function","Home Department","Location"};
		String[] arrayDimensions = {};
		List<String> accountsIDs = new ArrayList<String>();
		accountsIDs.add("Personnel.Headcount");
		String versionName = null;

		String personXMLStringValue = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		//List<String> accountsIDs = GetAccountsId.listOffAccountsIds();
		//accountsIDs.forEach((k) -> System.out.println("This is accont code = "+ k));
		try {

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element callElement = doc.createElement("call");
			callElement.setAttribute("method", "exportData");
			callElement.setAttribute("callerName", "Informatica");
			doc.appendChild(callElement);

			Element credentials = doc.createElement("credentials");
			credentials.setAttribute("login", "hailawadi@guidewire2.com");
			credentials.setAttribute("password", "Welcome@123");
			credentials.setAttribute("instanceCode", "GUIDEWIRE2");
			callElement.appendChild(credentials);


			Element version = doc.createElement("version");
			if(versionName != null) {
				version.setAttribute("name", versionName);
			}		
			version.setAttribute("isDefault", "false");

			callElement.appendChild(version);

			Element format = doc.createElement("format");
			format.setAttribute("useInternalCodes", "true");
			format.setAttribute("includeUnmappedItems", "false");
			callElement.appendChild(format);

			Element filters = doc.createElement("filters");
			Element accountsElement = doc.createElement("accounts");

			Element levels = doc.createElement("levels");
			Element level = doc.createElement("level");
			level.setAttribute("name", "Total Entity");
			level.setAttribute("isRollup", "true");
			level.setAttribute("includeDescendants", "false");
			levels.appendChild(level);

			Element timeSpan = doc.createElement("timeSpan");
			timeSpan.setAttribute("start", "01/2020");
			timeSpan.setAttribute("end", "03/2020");


			for(String code : accountsIDs) {

				Element accElement = doc.createElement("account");
				accElement.setAttribute("code", code);
				accElement.setAttribute("isAssumption", "false");
				accElement.setAttribute("includeDescendants", "false");
				accountsElement.appendChild(accElement);
			}

			filters.appendChild(accountsElement);
			filters.appendChild(levels);
			filters.appendChild(timeSpan);

			/*
			 * For Dimensions
			 */
			Element dimensions = doc.createElement("dimensions");
			Element dimension = doc.createElement("dimension");
			if(arrayDimensions.length>0) {
				for(String dimName: arrayDimensions){
					
					dimension.setAttribute("name", dimName);
					dimensions.appendChild(dimension);
				}
			}
			callElement.appendChild(filters);
			callElement.appendChild(dimensions);
			// Transform Document to XML String
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();

			transformer.transform(new DOMSource(doc), new StreamResult(writer));

			// Get the String value of final xml document
			personXMLStringValue = writer.getBuffer().toString();

		} 
		catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}

		System.out.println("personXMLStringValue = " + personXMLStringValue);
		return personXMLStringValue;
	}
}
