package infometry;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parse {
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
			String s = list.item(0).getTextContent();		
			
			Scanner scanner = new Scanner(s);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);

			}
			scanner.close();
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
