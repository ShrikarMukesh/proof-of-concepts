package accounts;

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

public class GetAccountsId {

	public static void main(String[] args) throws Exception {
		listOffAccountsIds();
	}
	public static List<String> listOffAccountsIds() throws Exception {

		List<String> accountsList = new ArrayList<String>();
		int totalAccountsID =0;
		String j = ExportAccountRequest.sendResponse();
		String xml= j;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		InputSource is;

		try {

			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("accounts");

			NodeList nodes = list.item(0).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i ++) {

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {	

					Element eElement = (Element) node;
					//System.out.println("Outer Account Code: "  + eElement.getAttribute("code"));
					String flag = eElement.getAttribute("code");

					accountsList.add(flag);
					//totalAccountsID++;

					//Inner accounts
					NodeList childNodes = node.getChildNodes();
					for (int z = 0; z < childNodes.getLength(); z ++) {

						Node innernode = childNodes.item(z);
						if (innernode.getNodeType() == Node.ELEMENT_NODE) {	

							Element inerElement = (Element) innernode;
							//System.out.println("Inner Account Code: "  + inerElement.getAttribute("code"));
							String counter = inerElement.getAttribute("code");
							accountsList.add(counter);
							totalAccountsID++;

							// Nested inner

							NodeList nestedNodes = innernode.getChildNodes();
							for (int m = 0; m < nestedNodes.getLength(); m ++) {

								Node nestedNode = nestedNodes.item(m);
								if (nestedNode.getNodeType() == Node.ELEMENT_NODE) {	

									Element nestedElement = (Element) nestedNode;
									//System.out.println("Nested Account Code: "  + nestedElement.getAttribute("code"));
									String nested = nestedElement.getAttribute("code");
									accountsList.add(nested);
									totalAccountsID++;

								}
							}
						}
					}

				}
			}

		} catch (ParserConfigurationException |SAXException | IOException e) {
			e.printStackTrace();
		} 

		accountsList = accountsList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		accountsList.forEach((k) -> System.out.println("This is accont code = "+ k));
		
		System.out.println(totalAccountsID);
		
		return accountsList;
	}

}

