package  org.localstorm.extraction.jsonpath.internal;

import org.localstorm.extraction.jsonpath.*;
import org.localstorm.extraction.jsonpath.spi.JsonProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.localstorm.extraction.jsonpath.internal.Utils.*;

/**
 * User: kalle
 * Date: 8/30/13
 * Time: 12:17 PM
 */
public class JsonReader implements org.localstorm.extraction.jsonpath.ParseContext, org.localstorm.extraction.jsonpath.ReadContext {

    private final org.localstorm.extraction.jsonpath.Configuration configuration;
    private Object json;

    public JsonReader() {
        this(org.localstorm.extraction.jsonpath.Configuration.defaultConfiguration());
    }

    public JsonReader(JsonProvider jsonProvider) {
        this(org.localstorm.extraction.jsonpath.Configuration.builder().jsonProvider(jsonProvider).build());
    }

    public JsonReader(org.localstorm.extraction.jsonpath.Configuration configuration) {
        notNull(configuration, "configuration can not be null");
        this.configuration = configuration;
    }

    //------------------------------------------------
    //
    // ParseContext impl
    //
    //------------------------------------------------
    @Override
    public org.localstorm.extraction.jsonpath.ReadContext parse(Object json) {
        notNull(json, "json object can not be null");
        this.json = json;
        return this;
    }

    @Override
    public org.localstorm.extraction.jsonpath.ReadContext parse(String json) {
        notEmpty(json, "json string can not be null or empty");
        this.json = configuration.getProvider().parse(json);
        return this;
    }

    @Override
    public org.localstorm.extraction.jsonpath.ReadContext parse(InputStream json) {
        notNull(json, "json input stream can not be null");
        this.json = configuration.getProvider().parse(json);
        return this;
    }

    @Override
    public org.localstorm.extraction.jsonpath.ReadContext parse(File json) throws IOException {
        notNull(json, "json file can not be null");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(json);
            parse(fis);
        } finally {
            Utils.closeQuietly(fis);
        }
        return this;
    }

    //------------------------------------------------
    //
    // ReadContext impl
    //
    //------------------------------------------------
    @Override
    public Object json() {
        return json;
    }

    @Override
    public <T> T read(String path, org.localstorm.extraction.jsonpath.Filter... filters) {
        notEmpty(path, "path can not be null or empty");
        return read(org.localstorm.extraction.jsonpath.JsonPath.compile(path, filters));
    }

    @Override
    public <T> T read(org.localstorm.extraction.jsonpath.JsonPath path) {
        notNull(path, "path can not be null");
        return path.read(json, configuration);
    }

}
