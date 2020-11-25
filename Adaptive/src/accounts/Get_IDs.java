package accounts;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

public class Get_IDs {

	static List<String> accountsList = new ArrayList<String>();
	static int totalAccountsID =0;

	public static void main(String[] args) throws Exception {
		listOffAccountsIds();
	}
	public static List<String> listOffAccountsIds() throws Exception {

		String j = ExportAccountRequest.sendResponse();
		String xml= j;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		InputSource is;

		try {

			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("account");

			NodeList nodes = list.item(0).getChildNodes();

			for (int i = 0; i < nodes.getLength(); i ++) {

				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {	

					//Element eElement = (Element) node;
					//System.err.println("Outer Account Name: "  + eElement.getAttribute("name"));

					NodeList childNodes = node.getChildNodes();
					for (int z = 0; z < childNodes.getLength(); z ++) {

						Node innernode = childNodes.item(z);
						if (innernode.getNodeType() == Node.ELEMENT_NODE) {	

							Element inerElement = (Element) innernode;

							String counter = inerElement.getAttribute("code");
							if(!counter.equals("")) {
								//System.out.println("Inner Account Code: "  + counter);
								accountsList.add(counter);
								innernode.hasChildNodes();
								totalAccountsID++;
							}
							else {
								//System.err.println("Outer Account Name: "  + inerElement.getAttribute("name"));

								innerMethod(innernode.getChildNodes(),accountsList);
							}
						}
					}
				}
			}

		} catch (ParserConfigurationException |SAXException | IOException e) {
			e.printStackTrace();
		}

		Stream<String> filter = accountsList.stream().filter(string -> !string.isEmpty());
		accountsList = filter.collect(Collectors.toList());		
		return accountsList;
	}

	public static void innerMethod(NodeList childNodes,List<String> accountsList) {

		for (int z = 0; z < childNodes.getLength(); z ++) {

			Node innernode = childNodes.item(z);
			if (innernode.getNodeType() == Node.ELEMENT_NODE) {	

				Element inerElement = (Element) innernode;
				String counter = inerElement.getAttribute("code");
				if(!counter.equals("")) {
					//System.out.println("Inner Account Code: "  + counter);
					accountsList.add(counter);
					innernode.hasChildNodes();
					totalAccountsID++;
				}
				else {
					//System.err.println("Outer Account Name: "  + inerElement.getAttribute("name"));
					innerMethod(innernode.getChildNodes(),accountsList);
				}
			}
		}

	}
}


