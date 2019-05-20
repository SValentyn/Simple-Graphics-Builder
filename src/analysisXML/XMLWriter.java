package analysisXML;

import beans.DataNode;
import beans.DataSheet;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Write data from a table to a XML-file.
 */
public class XMLWriter {

    public static void recordDataToFile(DataSheet dataSheet, String fileName) throws TransformerException, ParserConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
        transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1251");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        Document document = getDocument(dataSheet);
        DOMImplementation domImpl = document.getImplementation();
        DocumentType doctype = domImpl.createDocumentType("dataSheet", "", "src/XML/XMLDTD.dtd");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

        transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
    }

    private static Document getDocument(DataSheet dataSheet) throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("dataSheet");
        document.appendChild(root);

        for (DataNode node : dataSheet.getNodes()) {
            Element data = document.createElement("data");

            Attr date = document.createAttribute("date");
            date.setValue(node.getDate());
            data.setAttributeNode(date);

            Element elementX = document.createElement("x");
            elementX.appendChild(document.createTextNode(node.getX() + ""));
            data.appendChild(elementX);

            Element elementY = document.createElement("y");
            elementY.appendChild(document.createTextNode(node.getY() + ""));
            data.appendChild(elementY);

            root.appendChild(data);
        }
        return document;
    }

}
