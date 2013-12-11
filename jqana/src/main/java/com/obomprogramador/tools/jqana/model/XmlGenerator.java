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

import org.w3c.dom.Document;

/**
 * Generate a XML Document with all metrics found.
 * @author Cleuton Sampaio.
 *
 */
public interface XmlGenerator {
	
	/**
	 * Generate a XML Document, according to the format:
	 * 
		<?xml version="1.0" encoding="UTF-8"?>
		<jqana-report>
		    <version></version>
		    <date></date>
		    <project></project>
		    <package-summary>
		        <package>
		            <name></name>
		            <metrics>
		                <metric>
		                    <name></name>
		                    <value></value>
		                    <violated></violated>
		                    <message></message>
		                </metric>
		            </metrics>
		        </package>
		    </package-summary>
		    <package-detail>
		        <package>
		            <class>
		                <name></name>
		                <metric>
		                    <name></name>
		                    <value></value>
		                    <violated></violated>
		                    <message></message>
		                </metric>
		                <methods>
		                    <method>
			                    <name></name>
			                    <metric>
			                    	<name></name>
			                    	<value></value>
			                    	<violated></violated>
			                    	<message></message>
			                	</metric>
		                    </method>
		                </methods>
		            </class>
		        </package>
		    </package-detail>
		</jqana-report>
	 *
	 * @param measurements List of measurements from the source project.
	 * @return XML Document.
	 */
	public Document serialize(List<Measurement> measurements);
}
