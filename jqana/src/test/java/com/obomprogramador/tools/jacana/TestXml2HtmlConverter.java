package com.obomprogramador.tools.jacana;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXml2HtmlConverter;

public class TestXml2HtmlConverter {

	@Test
	public void test() {
		Logger logger =LoggerFactory.getLogger(this.getClass());
		String sourceXml = this.getSource("test.xml");
		DefaultXml2HtmlConverter converter = new DefaultXml2HtmlConverter();
		try {
			String output = converter.convert(sourceXml);
			assertTrue(output != null);
			logger.debug("Output HTML: " + output);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (TransformerException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
		
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
