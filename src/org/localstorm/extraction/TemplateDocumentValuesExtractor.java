package org.localstorm.extraction;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class TemplateDocumentValuesExtractor extends DocumentValuesExtractor {

    public TemplateDocumentValuesExtractor(File template) throws IOException {
        super();
        init(template);
    }

    private void init(File template) throws IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(template);
            doc.getDocumentElement().normalize();
            init(doc);
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(e);
        }
    }

    private void init(Document doc) throws IOException {
        try {
            NodeList nl = XPathAPI.selectNodeList(doc, "/template/param");
            for (int i = 0; i < nl.getLength(); i++) {
                Node param = nl.item(i);
                NamedNodeMap attrs = param.getAttributes();

                String value = param.getTextContent();
                String name = getStringAttr(attrs, "name");
                String type = getStringAttr(attrs, "type");
                if (type == null) {
                    throw new IOException("Unable to find param attribute 'type': " + param);
                }
                type = type.toLowerCase();

                switch (type) {
                    case "xsl":
                        addExtractor(name, new XslValueExtractor(value));
                        break;
                    case "xpath":
                        addExtractor(name, new XPathValueExtractor(value));
                        break;
                    case "const":
                        addExtractor(name, new ConstantValueExtractor(value));
                        break;
                    default:
                        throw new IOException("Unsupported param type: " + type);
                }
            }
        } catch (TransformerException e) {
            throw new IOException(e);
        }
    }

    private String getStringAttr(NamedNodeMap attrs, String name) throws IOException {
        Node n = attrs.getNamedItem(name);
        if (n.getNodeType() != Node.ATTRIBUTE_NODE) {
            throw new IOException("Unable to read attribute [" + name + "] from " + attrs);
        }
        return n.getNodeValue();
    }
}
