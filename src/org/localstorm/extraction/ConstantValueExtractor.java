package org.localstorm.extraction;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class ConstantValueExtractor implements ValueExtractor {

    private final String value;

    public ConstantValueExtractor(String constant) {
        this.value = constant;
    }

    @Override
    public String getValue(SourceDocument doc) throws UnsupportedDocumentType {
        return value;
    }
}
