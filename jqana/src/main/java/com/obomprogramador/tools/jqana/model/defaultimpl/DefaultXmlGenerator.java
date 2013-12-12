/**
 * jQana - Open Source Java(TM) code quality analyzer.
 * 
 * Copyright 2013 Cleuton Sampaio de Melo Jr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project website: http://www.jqana.com
 */
package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.XmlGenerator;

/**
 * Default implementation of XmlGenerator.
 * 
 * @see XmlGenerator
 * 
 *
 * 
 * @author Cleuton Sampaio
 *
 */
public class DefaultXmlGenerator implements XmlGenerator {

	public DefaultXmlGenerator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the list of measurements, and classifies it according to 
	 * package and class. 
	 * The aggregated values are calculated as simple average, i.e.
	 * the Class value for Cyclomatic Complexity is the average of it's
	 * methods Cyclomatic Complexity metrics.
	 * @throws JAXBException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	@Override
	public Document serialize(Measurement measurement) throws JAXBException, ParserConfigurationException, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
		Document report = db.newDocument();
	    JAXBContext context = JAXBContext.newInstance(ProjectMeasurement.class);

	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    m.marshal((ProjectMeasurement)measurement, report);		
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.transform(new DOMSource(report), new StreamResult(System.out));
		return report;
	}

}
