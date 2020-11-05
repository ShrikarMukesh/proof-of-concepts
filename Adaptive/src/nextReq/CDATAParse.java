package nextReq;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
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

public class CDATAParse {
	public static void main(String[] args) throws Exception {
		String j = ExportData.sendResponse();
		String xml=j;

		System.out.println(xml);
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

						String lineString = tokens.nextToken();

						StringBuilder sb = new StringBuilder();
						List<String> arrList = new ArrayList<>();

						for(int m=0;m<lineString.length();m++) {

							if(lineString.charAt(m) != ',') {			
								sb.append(lineString.charAt(m));
							}
							else {
								if(m -1 >= 0 && m+1<lineString.length() -1) {

									if(lineString.charAt(m-1) == ',' && lineString.charAt(m-1) != '"' && lineString.charAt(m+1) != '"' ) {

										sb.append(lineString.charAt(m));
									}
									else {
										arrList.add(sb.toString());
										sb = new StringBuilder();
									}
								}

							}
							if(m == lineString.length() -1) {

								String lastValue = sb.toString();
								lastValue = lastValue.replace("=NA()*", "");

								String[] lastArray = lastValue.split(",");
								arrList.addAll(Arrays.asList(lastArray));
							}
						}
						arrList.forEach((p)->System.out.print(p+"   "));
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
