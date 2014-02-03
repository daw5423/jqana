package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.model.Xml2HtmlConverter;

/**
 * Default implementation of Xml2HtmlConverter.
 * 
 * @see Xml2HtmlConverter
 * @author Cleuton Sampaio
 * 
 */
public class DefaultXml2HtmlConverter implements Xml2HtmlConverter {

    protected Logger logger;

    /**
     * Default constructor.
     */
    public DefaultXml2HtmlConverter() {
        super();
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public String convert(String sourceReport)
            throws TransformerException,
            UnsupportedEncodingException {
        String output = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(this.getClass()
                .getClassLoader().getResourceAsStream("xml2html.xsl"));
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(new ByteArrayInputStream(
                sourceReport.getBytes("UTF-8")));
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        StreamResult out = new StreamResult(bOut);
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        transformer.transform(in, out);
        output = new String(bOut.toByteArray(), "UTF-8");
        return output;
    }

}
