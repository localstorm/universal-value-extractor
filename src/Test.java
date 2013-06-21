import org.localstorm.extraction.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Map;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class Test {

    public static void main(String[] args) throws Exception {
        DocumentValuesExtractor dve = new TemplateDocumentValuesExtractor(new File("template.xml"));

        File fXmlFile = new File("test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();


        Map<String, String> result = dve.extract(new XmlSourceDocument(doc));
        System.out.println(result);
    }
}
