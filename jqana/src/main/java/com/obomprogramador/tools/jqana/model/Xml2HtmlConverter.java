package com.obomprogramador.tools.jqana.model;

import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

/**
 * Transforms the generated XML report into an HTML fragment, 
 * in order to insert it into the Doxia report.
 * 
 * @author Cleuton Sampaio
 *
 */
public interface Xml2HtmlConverter {
	/**
	 * Convert the JAXB generated XML file into an HTNL fragment, using 
	 * XSLT.
	 * The HTML fragment does not include the tags:
	 * - <html>
	 * - <head>
	 * - <body>
	 * @param sourceReport soure XML file.
	 * @return HTML fragment.
	 * @throws TransformerConfigurationException if there is any XSLT configuration problem
	 * @throws TransformerException if there is any transform error.
	 * @throws UnsupportedEncodingException 
	 */
	public String convert(String sourceReport) throws TransformerConfigurationException, TransformerException, UnsupportedEncodingException;
}
