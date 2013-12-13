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
package com.obomprogramador.tools.jqana.model;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;



/**
 * Generate a XML Document with all metrics found.
 * @author Cleuton Sampaio.
 *
 */
public interface XmlGenerator {
	
	/**
	 * Generate a XML Document from a measurement. It uses JAXB to do that.
	 * 
	 *
	 * @param measurements List of measurements from the source project.
	 * @return XML Document.
	 */

	public Document serialize(Measurement measurement) throws JAXBException,ParserConfigurationException,TransformerException;
}
