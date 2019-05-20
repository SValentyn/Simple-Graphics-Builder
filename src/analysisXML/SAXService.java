package analysisXML;

import beans.DataNode;
import beans.DataSheet;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SAXService {

    public static DataSheet parseWithDTD(String nameFile) {
        DataHandler handler = new DataHandler();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);

            SAXParser parser = factory.newSAXParser();
            parser.parse(new FileInputStream(nameFile), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            DataSheet dataSheet = new DataSheet();
            dataSheet.addData(new DataNode());
            return dataSheet;
        }

        return handler.getDataSheet();
    }

    /* Additional implementation of parsing files using XSD schemes. */
    public static DataSheet parseWithXSD(String nameFile) throws SAXException, ParserConfigurationException, IOException {
        DataHandler handler = new DataHandler();
        SAXParser parser = createParser();
        parser.parse(new FileInputStream(new File(nameFile)), handler);
        return handler.getDataSheet();
    }

    private static SAXParser createParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setSchema(getSchema());
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        SAXParser parser = factory.newSAXParser();
        parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        return parser;
    }

    private static Schema getSchema() throws SAXException {
        String xmlSchema = XMLConstants.W3C_XML_SCHEMA_NS_URI; // "http://www.w3.org/2001/XMLSchema"
        SchemaFactory schemaFactory = SchemaFactory.newInstance(xmlSchema);
        return schemaFactory.newSchema(new File("/XML/XMLSchema.xsd"));
    }

}
