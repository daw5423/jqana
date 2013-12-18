package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Xml2HtmlConverter;

/**
 * Default implementation of Xml2HtmlConverter.
 * @see Xml2HtmlConverter
 * @author Cleuton Sampaio
 *
 */
public class DefaultXml2HtmlConverter implements Xml2HtmlConverter {

	protected Logger logger;
	
	public DefaultXml2HtmlConverter() {
		super();
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public String convert(String sourceReport) throws TransformerConfigurationException, TransformerException, UnsupportedEncodingException  {
		String output = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(this.getClass().getClassLoader().getResourceAsStream("xml2html.xsl"));
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(new ByteArrayInputStream(sourceReport.getBytes("UTF-8")));
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        StreamResult out = new StreamResult(bOut);
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        transformer.transform(in, out);
        output = new String(bOut.toByteArray(), "UTF-8");
		return output;
	}
	
	private String getSource(String string) {
		String sourceUri = this.getClass().getClassLoader().getResource(string).getFile();
		String sourceCode = null;
		sourceCode = readFile(sourceUri);
		return sourceCode;
	}
	
	private String readFile(String fileName)  {
		BufferedReader br = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	    	br = new BufferedReader(new FileReader(fileName));
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return sb.toString();
	}


}
