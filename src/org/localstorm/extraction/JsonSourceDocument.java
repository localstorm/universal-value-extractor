package org.localstorm.extraction;

import org.localstorm.extraction.jsonpath.JsonPath;
import org.localstorm.extraction.jsonpath.ReadContext;

/**
 * @author localstorm
 *         Date: 05.12.13
 */
public class JsonSourceDocument implements SourceDocument {

    private final String jsonSrc;
    private final ReadContext json;

    public JsonSourceDocument(String json) throws Exception {
        this.jsonSrc = json;
        this.json = JsonPath.parse(json);
    }

    @Override
    public ReadContext getDocument() {
        return json;
    }

    @Override
    public String toString() {
        return jsonSrc;
    }
}
