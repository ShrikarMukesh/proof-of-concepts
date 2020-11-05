package aftermeeting;

import java.io.StringWriter;
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

public class PersonXMLDocument {

    public static void main(String[] args) {
        String personXMLStringValue = null;

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Create Person root element 
            Element personRootElement = doc.createElement("Person");
            doc.appendChild(personRootElement);

            // Create First Name Element
            Element firstNameElement = doc.createElement("FirstName");
            firstNameElement.appendChild(doc.createTextNode("Sergey"));
            personRootElement.appendChild(firstNameElement);

            // Create Last Name Element
            Element lastNameElement = doc.createElement("LastName");
            lastNameElement.appendChild(doc.createTextNode("Kargopolov"));
            personRootElement.appendChild(lastNameElement);

            // Transform Document to XML String
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();

            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            // Get the String value of final xml document
            personXMLStringValue = writer.getBuffer().toString();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("personXMLStringValue = " + personXMLStringValue);

    }

}