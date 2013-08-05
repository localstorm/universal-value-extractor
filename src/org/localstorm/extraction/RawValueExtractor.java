package org.localstorm.extraction;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class RawValueExtractor implements ValueExtractor {

    public RawValueExtractor() {
    }

    @Override
    public String getValue(SourceDocument doc) throws UnsupportedDocumentType {
        if (doc == null) {
            return null;
        } else {
            return doc.toString();
        }
    }
}
