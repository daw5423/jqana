<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/measurement">
		<table >
		<tr>
		<th style="text-align:left;">Package name</th>
		<th>CC</th>
		<th>LCOM4</th>
		<th>RFC</th>
		</tr>
		<br/>
		 <xsl:for-each select="innerMeasurements/measurement">
		 			<br/>
                    <tr>
                    <td>
                    <xsl:value-of select="name"/>
                    </td>
                    <xsl:for-each select="metricsValues/metricValue">
                        <td style="text-align:center;">
                        <xsl:if test="violated = &apos;true&apos;">
                        	<span style="font-weight:bold;color:red"><xsl:value-of select="value"/></span>
                        </xsl:if>
                        <xsl:if test="violated = &apos;false&apos;">
                        	<xsl:value-of select="value"/>
                        </xsl:if>
                        </td>
                    </xsl:for-each>
                    </tr>
                 </xsl:for-each>
		</table>
    </xsl:template>
</xsl:stylesheet> 