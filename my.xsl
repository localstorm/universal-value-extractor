<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:template match="/data/to"><xsl:value-of select="text()"/></xsl:template>
    <xsl:template match="text()"/>
</xsl:stylesheet>