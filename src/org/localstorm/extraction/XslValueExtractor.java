package org.localstorm.extraction;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class XslValueExtractor implements ValueExtractor {

    private String xsl;

    public XslValueExtractor(String xsl) {
        this.xsl = xsl;
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(new StringReader(xsl)));
            transformer.transform(new DOMSource((Document) doc.getDocument()), new StreamResult(baos));
            return baos.toString();
        } catch (TransformerException e) {
            throw new IOException(e);
        }
    }
}
