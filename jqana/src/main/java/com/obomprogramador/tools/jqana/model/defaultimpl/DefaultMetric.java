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

import com.obomprogramador.tools.jqana.model.LimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.Metric;

/**
 * Default Metric implementation.
 * @see Metric
 * @author Cleuton Sampaio.
 *
 */
public class DefaultMetric implements Metric, Comparable<Metric> {
	
	
	private String metricName;
	private String message;
	private LimitVerificationAlgorithm lva;

	public DefaultMetric() {
		super();
	}
	
	

	public DefaultMetric(String metricName, String message) {
		super();
		this.metricName = metricName;
		this.message = message;
	}


	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public LimitVerificationAlgorithm getLva() {
		return lva;
	}



	public void setLva(LimitVerificationAlgorithm lva) {
		this.lva = lva;
	}



	@Override
	public String toString() {
		return "[Metric:  metricName="
				+ this.metricName
				+ "; limitVerificationAlgorithm=" 
				+ this.lva.getClass().getSimpleName()
				+ "]";
	}



	@Override
	public String getMetricName() {
		return this.metricName;
	}

	@Override
	public void setMetricName(String name) {
		this.metricName = name;
	}


	@Override
	public String getMetricMessage() {
		return this.message;
	}

	@Override
	public void setMetricMessage(String message) {
		this.message = message;
	}



	@Override
	public LimitVerificationAlgorithm getVerificationAlgorithm() {
		return this.lva;
	}



	@Override
	public void setVerificationAlgorithm(LimitVerificationAlgorithm lva) {
		this.lva = lva;
	}



	@Override
	public int hashCode() {
		return this.getMetricName().hashCode();
	}



	@Override
	public boolean equals(Object obj) {
		return this.getMetricName().equalsIgnoreCase(((Metric) obj).getMetricName());
	}



	@Override
	public int compareTo(Metric o) {
		return this.getMetricName().compareToIgnoreCase(o.getMetricName());
	}

	
}
