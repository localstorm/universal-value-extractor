package org.localstorm.extraction;

import org.w3c.dom.Document;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class XmlSourceDocument implements SourceDocument {
    private final Document doc;

    public XmlSourceDocument(Document doc) {
        this.doc = doc;
    }

    @Override
    public Document getDocument() {
        return doc;
    }
}
