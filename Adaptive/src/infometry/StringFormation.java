package infometry;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class StringFormation {
	public static void main(String[] args) throws Exception {

		String j = ExportDataRequest.sendResponse();
		String xml= j;
		//System.out.println(xml);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		InputSource is;

		try {

			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("output");
			NodeList chNodes = list.item(0).getChildNodes();

			for (int i = 0; i < chNodes.getLength(); i ++) {

				Node node = chNodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println("This is ELEMENT\n\n");
				}

				if (node.getNodeType() == Node.CDATA_SECTION_NODE) {

					System.out.println("This is CDATA_SECTION_NODE\n\n");

					CDATASection sec = (CDATASection) node;
					String str = sec.getData();
					StringTokenizer tokens = new StringTokenizer(str, "\n");

					while (tokens.hasMoreTokens()) {
						List<String> arrayList = new ArrayList<>();
						String token = tokens.nextToken();
						String splittedToken[] = token.split("\"");
						for (int k = 0; k < splittedToken.length; k ++) {
							String temp = splittedToken[k];
							temp = temp.trim();
							if (!temp.isEmpty()) {

								if (temp.startsWith(",") || temp.endsWith(",")) {

									String[] comSepValues = temp.split(",");
									for (int l = 0; l < comSepValues.length; l ++) {
										String trimmedVal = comSepValues[l].trim();
										if (!trimmedVal.isEmpty()) {
											arrayList.add(comSepValues[l]);
										}

									}

								}
								else {
									arrayList.add(temp);
								}								
							}
						}

						arrayList.forEach((p)->System.out.print(p+"  "));
						System.out.println();
					}

				}
			}
		} catch (ParserConfigurationException e) {

		} catch (SAXException e) {

		} catch (IOException e) {

		}
	}
	public static String getResponce(String str) {
		return str;
	}
}
