import org.localstorm.extraction.DocumentValuesExtractor;
import org.localstorm.extraction.JsonSourceDocument;
import org.localstorm.extraction.TemplateDocumentValuesExtractor;
import org.localstorm.extraction.XmlSourceDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Map;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class TestJSON {

    public static void main(String[] args) throws Exception {
        DocumentValuesExtractor dve = new TemplateDocumentValuesExtractor(new File("templateJSON.xml"));
        String json = "{ \"firstName\":\"John\" , \"lastName\":\"Doe\", \"objects\": [1, 2, 3]  }";
        Map<String, String> result = dve.extract(new JsonSourceDocument(json));
        System.out.println("Result: "+result);
    }
}
