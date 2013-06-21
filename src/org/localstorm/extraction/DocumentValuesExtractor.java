package org.localstorm.extraction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class DocumentValuesExtractor {
    private Map<String, ValueExtractor> propertyExtractors = new HashMap<>();

    public DocumentValuesExtractor() {
    }

    public void addExtractor(String propertyName, ValueExtractor extractor) {
        this.propertyExtractors.put(propertyName, extractor);
    }

    public Map<String, String> extract(SourceDocument doc) throws IOException, UnsupportedDocumentType {
        Map<String, String> res = new HashMap<>();
        for (Map.Entry<String, ValueExtractor> e: propertyExtractors.entrySet()) {
            String prop = e.getKey();
            String extracted = e.getValue().getValue(doc);
            res.put(prop, extracted);
        }
        return res;
    }
}
