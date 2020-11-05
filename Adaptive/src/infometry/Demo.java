package infometry;

import java.io.IOException;
import java.io.StringReader;
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

public class Demo {
	public static void main(String[] args) throws Exception {

		String j = ExportDataRequest.sendResponse();
		String xml= j;

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
					//str.replaceAll("=NA()*", " ");
					StringTokenizer tokens = new StringTokenizer(str, "\n");
					
					while (tokens.hasMoreTokens()) {
						
						String token = tokens.nextToken();
						//StringTokenizer childTokens = new StringTokenizer(token, ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
						StringTokenizer childTokens = new StringTokenizer(token, ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
						while (childTokens.hasMoreTokens()) {
							String data = childTokens.nextToken();
							System.out.print(data + " ");
						} 
						System.out.println();
					}
					
				}
			}
			
			/*Scanner scanner = new Scanner(s);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);

			}
			scanner.close();*/
			//new BufferedReader(new StringReader(s)).lines().forEach(System.out::println);

		} catch (ParserConfigurationException e) {

		} catch (SAXException e) {

		} catch (IOException e) {

		}
	}
	public static String getResponce(String str) {
		return str;
	}
}
