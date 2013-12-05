package org.localstorm.extraction;

/**
 * @author localstorm
 *         Date: 05.12.13
 */
public class JsonSourceDocument implements SourceDocument {

    private final String json;

    public JsonSourceDocument(String json) throws Exception {
        this.json = json;
    }

    @Override
    public String getDocument() {
        return json;
    }

    @Override
    public String toString() {
        return json;
    }
}
