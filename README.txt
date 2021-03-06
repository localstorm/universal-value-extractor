universal-value-extractor
======================

A simple utility to extract property values from arbitrary XML/JSON using XPATH, XSL, JSONPath or to fill values with constants

HOW TO USE:

Say you have an XML file like this:

<?xml version="1.0" encoding="UTF-8"?>
<data>
    <to>Some Guy</to>
    <subject>Some subject</subject>
    <body>Body text</body>
</data>

Use this template:

<?xml version="1.0" encoding="UTF-8"?>
<template>
    <!-- Extract data from XML: -->
    <param name="subject" type="xsl">
        <![CDATA[
        <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
            <xsl:strip-space elements="*"/>
            <xsl:output method="text"/>
            <xsl:template match="/data/subject">
                <xsl:value-of select="text()"/>
            </xsl:template>
            <xsl:template match="text()"/>
        </xsl:stylesheet>
        ]]>
    </param>

    <param name="body" type="xsl">
        <![CDATA[
        <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
            <xsl:strip-space elements="*"/>
            <xsl:output method="text"/>
            <xsl:template match="/data/body">
                <xsl:value-of select="text()"/>
            </xsl:template>
            <xsl:template match="text()"/>
        </xsl:stylesheet>
        ]]>
    </param>

    <param name="to" type="xpath">/data/to</param>

    <!-- Constants: -->
    <param name="contentType" type="const">text/plain; charset=UTF-8</param>
    <param name="from" type="const">info@from.from</param>
    <param name="rawXml" type="raw"/>

</template>

Save it to a file and use this code to extract subject, body, from, to and contentType:

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

The OUTPUT will be:
{to=Some Guy, body=Body text, subject=Some subject, xml=<data>
    <to>Some Guy</to>
    <subject>Some subject</subject>
    <body>Body text</body>
</data>
, contentType=text/plain; charset=UTF-8, from=from=info@from.from}

Template file for JSON:

<?xml version="1.0" encoding="UTF-8"?>
<template>
    <!-- Extract data from JSON: -->
    <!-- JSON Path is based on https://github.com/localstorm/JsonPath -->
    <param name="value0" type="json">$.objects[0]</param>
    <param name="value1" type="json">$.objects[1]</param>
    <param name="value2" type="json">$.objects[2]</param>
    <param name="value3" type="json">$.objects[3]</param>
    <param name="firstName" type="json">$.firstName</param>
</template>

Save it to a file and use this code to extract values:

public static void main(String[] args) throws Exception {
        DocumentValuesExtractor dve = new TemplateDocumentValuesExtractor(new File("templateJSON.xml"));
        
        String json = "{ \"firstName\":\"John\" , " +
                      "\"lastName\":\"Doe\", " +
                      "\"objects\": [1, 2, 3]  }";
                      
        Map<String, String> result = dve.extract(new JsonSourceDocument(json));
        System.out.println("Result: "+result);
}

The OUTPUT will be:

Result: {value0=1, value3=null, firstName=John, value1=2, value2=3}
