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

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a metric.
 * @author Cleuton Sampaio
 *
 */

public abstract class Metric {
	/**
	 * Metric name getter.
	 * @return the metric's name.
	 */
	public abstract String 	getMetricName();
	/**
	 * Metric name setter.
	 * @param name the metric's name.
	 */
	public abstract void     setMetricName(String name);
	/**
	 * Metric message getter.
	 * @return the metric's violation message.
	 */
	public abstract String   getMetricMessage();
	/**
	 * Metric message setter.
	 * @param message the metric's violation message.
	 */
	public abstract void     setMetricMessage(String message);
	/**
	 * Metric's verification algorithm getter.
	 * @return The Metric's verification algorithm.
	 */
	
	public abstract LimitVerificationAlgorithm getVerificationAlgorithm();
	/**
	 * Metric's verification algorithm setter.
	 * @param lva The Metric's verification algorithm.
	 */
	public abstract void     setVerificationAlgorithm(LimitVerificationAlgorithm lva);
}
