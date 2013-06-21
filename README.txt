xml-property-extractor
======================

A simple utility to extract property values from arbitrary XML using XPATH, XSL or to fill values with constants

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
{to=Some Guy, body=Body text, subject=Some subject, contentType=text/plain; charset=UTF-8, from=info@from.from}
