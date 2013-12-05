package org.localstorm.extraction;

import org.localstorm.extraction.jsonpath.JsonPath;

import java.io.IOException;

/**
 * @author localstorm
 *         Date: 05.12.13
 */
public class JsonValueExtractor implements ValueExtractor {

    private final String jsonPath;

    public JsonValueExtractor(String key) {
        this.jsonPath = key;
    }

    @Override
    public String getValue(SourceDocument doc) throws UnsupportedDocumentType, IOException {
        if (doc == null) {
            throw new IllegalArgumentException("Document can't be null");
        }

        if (!(doc instanceof JsonSourceDocument)) {
            throw new UnsupportedDocumentType(this.getClass() + " doesn't support " + doc.getClass());
        }

        String json = ((JsonSourceDocument) doc).getDocument();
        if (json == null) {
            return null;
        }
        try {
            Object result = JsonPath.read(json, jsonPath);
            return (result != null) ? result.toString() : null;
        } catch(Exception e) {
            return null;
        }
    }

}
