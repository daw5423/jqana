<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/measurement">
    	<h2>Project summary</h2>
		<table style="width: 100%;">
		<tr>
		<th style="text-align:left; width: 50%">Package name</th>
		<th>CC (average)</th>
		<th>LCOM4 (highest)</th>
		<th>RFC (average)</th>
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
                    	
                 	<table  style="width: 100%;">
					<tr>
					<th style="text-align:left; width: 50%">Class name</th>
					<th>CC (average)</th>
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
      		
      		
      		<h2>Class Detail</h2>
		
			<xsl:for-each select="innerMeasurements/measurement">
				<xsl:for-each select="innerMeasurements/measurement">
                    <a>
                    	<xsl:attribute name="name">
        					<xsl:value-of select="name"/>
      					</xsl:attribute>
      					<h3>
                    		<xsl:value-of select="name"/>
                    	</h3>
					</a>      					
                    	
                 	<table  style="width: 100%;">
					<tr>
					<th style="text-align:left; width: 50%">Method name</th>
					<th>CC</th>
					</tr>
						<xsl:for-each select="innerMeasurements/measurement">
			                    <tr>
			                    <td>
		        					<xsl:value-of select="name"/>
			                    </td>
			                    <xsl:for-each select="metricsValues/metricValue">
			                    	<td style="text-align:center;">
			                    	<xsl:if test="name = &apos;Cyclomatic complexity&apos;">
				                        <xsl:if test="violated = &apos;true&apos;">
				                        	<span style="font-weight:bold;color:red"><xsl:value-of select="value"/></span>
				                        </xsl:if>
				                        <xsl:if test="violated = &apos;false&apos;">
				                        	<xsl:value-of select="value"/>
				                        </xsl:if>
			                    	</xsl:if>
			                        </td>
			                    </xsl:for-each>
			                    </tr>
			      		</xsl:for-each>
					</table>
				</xsl:for-each>
      		</xsl:for-each>
		
    </xsl:template>
</xsl:stylesheet> 