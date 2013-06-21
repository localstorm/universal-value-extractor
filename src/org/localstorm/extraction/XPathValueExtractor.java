package org.localstorm.extraction;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class XPathValueExtractor implements ValueExtractor {

    private final String xpath;

    public XPathValueExtractor(String xpath) {
        this.xpath = xpath;
    }

    @Override
    public String getValue(SourceDocument doc) throws UnsupportedDocumentType, IOException {
        if (doc == null) {
            throw new IllegalArgumentException("Document can't be null");
        }

        if (!(doc instanceof XmlSourceDocument)) {
            throw new UnsupportedDocumentType(this.getClass() + " doesn't support " + doc.getClass());
        }

        try {
            Node n = XPathAPI.selectSingleNode((Document) doc.getDocument(), xpath);
            if (n == null) {
                return null;
            }

            switch (n.getNodeType()) {
                case Node.TEXT_NODE:
                    return n.getTextContent();
                case Node.ATTRIBUTE_NODE:
                    return n.getNodeValue();
                case Node.ELEMENT_NODE:
                    return n.getTextContent();
                default:
                    throw new IOException("Unexpected node name/type: " + n.getNodeName() + "/" + n.getNodeType());
            }
        } catch (TransformerException e) {
            throw new IOException(e);
        }
    }


}
