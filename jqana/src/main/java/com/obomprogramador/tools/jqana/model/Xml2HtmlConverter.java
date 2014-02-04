package com.obomprogramador.tools.jqana.model;

import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;

/**
 * Transforms the generated XML report into an HTML fragment, in order to insert
 * it into the Doxia report.
 * 
 * @author Cleuton Sampaio
 * 
 */
public interface Xml2HtmlConverter {
    /**
     * Convert the JAXB generated XML file into an HTNL fragment, using XSLT.
     * The HTML fragment does not include the tags: - <html> - <head> - <body>
     * 
     * @param sourceReport
     *            soure XML file.
     * @return HTML fragment.
     * @throws TransformerException
     *             if there is any transform error.
     * @throws UnsupportedEncodingException If there is a problem
     *             with char encoding. 
     */
    String convert(String sourceReport)
            throws TransformerException,
            UnsupportedEncodingException;
}
