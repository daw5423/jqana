<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/measurement">
    	<h2>Project summary</h2>
		<table >
		<tr>
		<th style="text-align:left;">Package name</th>
		<th>CC</th>
		<th>LCOM4</th>
		<th>RFC</th>
		</tr>
			<xsl:for-each select="innerMeasurements/measurement">
                    <tr>
                    <td>
                    <a>
                    	<xsl:attribute name="href">
                    		<xsl:text>#</xsl:text>
        					<xsl:value-of select="name"/>
      					</xsl:attribute>
                    	<xsl:value-of select="name"/>
                    </a>
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
		
		
		<h2>Package Detail</h2>
		
			<xsl:for-each select="innerMeasurements/measurement">
                    <a>
                    	<xsl:attribute name="name">
        					<xsl:value-of select="name"/>
      					</xsl:attribute>
      					<h3>
                    		<xsl:value-of select="name"/>
                    	</h3>
					</a>      					
                    	
                 	<table >
					<tr>
					<th style="text-align:left;">Class name</th>
					<th>CC</th>
					<th>LCOM4</th>
					<th>RFC</th>
					</tr>
						<xsl:for-each select="innerMeasurements/measurement">
			                    <tr>
			                    <td>
			                    <a>
			                    	<xsl:attribute name="href">
			                    		<xsl:text>#</xsl:text>
			        					<xsl:value-of select="name"/>
			      					</xsl:attribute>
			                    	<xsl:value-of select="name"/>
			                    </a>
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
                    	
                    	
                    

      		</xsl:for-each>
		
    </xsl:template>
</xsl:stylesheet> 