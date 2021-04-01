package modeledsheet;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class GetAccountCodes {
	public static void main(String[] args) throws Exception {
		
		listOffAccountsIds();
	}
	public static List<String> listOffAccountsIds() throws Exception {

		List<String> accountsList = new ArrayList<String>();
		int totalAccountsID =0;
		String j = ExportAccModeledSheet.sendResponse();
		String xml= j;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		InputSource is;

		try {

			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("account");
			
			for (int i = 0; i < list.getLength(); i ++) {
				Node item = list.item(i);
				Element eElement = (Element) item;
				
				String flag = eElement.getAttribute("code");
				
				accountsList.add(flag);
				totalAccountsID++;
			}	
			
		} 
		catch (ParserConfigurationException |SAXException | IOException e) {
			e.printStackTrace();
		} 

		accountsList= accountsList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		accountsList.forEach((k) -> System.out.println("This is isAssumption  = "+ k));
		System.out.println(totalAccountsID);
		//System.out.println(accountsList);
		return accountsList;
	}
}
