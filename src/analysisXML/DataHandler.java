package analysisXML;

import beans.DataNode;
import beans.DataSheet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The class is used to implement file parsing.
 */
public class DataHandler extends DefaultHandler {

    private DataSheet dataSheet = null;
    private DataNode tmpNode = null;
    private boolean isX, isY;

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
    }

    @Override
    public void startDocument() {
        if (dataSheet == null) {
            dataSheet = new DataSheet();
            dataSheet.setTitle("New DataSheet");
        }
    }

    @Override
    public void endDocument() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case "data":
                tmpNode = new DataNode();
                if (attributes.getLength() > 0)
                    tmpNode.setDate(attributes.getValue(0));
                break;
            case "x":
                isX = true;
                break;
            case "y":
                isY = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "data":
                dataSheet.addData(tmpNode);
                tmpNode = null;
                break;
            case "x":
                isX = false;
                break;
            case "y":
                isY = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        if (isX) {
            tmpNode.setX(Double.parseDouble(str));
        } else if (isY) {
            tmpNode.setY(Double.parseDouble(str));
        }
    }

    @Override
    public void warning(SAXParseException exception) {
        System.err.println("Warning " + exception);
    }

    @Override
    public void error(SAXParseException exception) {
        System.err.println("Error " + exception);
    }

    @Override
    public void fatalError(SAXParseException exception) {
        System.err.println("Fatal Error " + exception);
    }

}
