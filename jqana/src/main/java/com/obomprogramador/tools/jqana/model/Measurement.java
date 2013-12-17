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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.obomprogramador.tools.jqana.context.GlobalConstants;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

/**
 * This represents a measurement taken from source code. It may have some inner measurements.
 * @author Cleuton Sampaio
 *
 */
@XmlRootElement
public class Measurement implements Comparable<Measurement>{
	
	public static enum MEASUREMENT_TYPE {
									PROJECT_MEASUREMENT,
									PACKAGE_MEASUREMENT,
									CLASS_MEASUREMENT,
									METHOD_MEASUREMENT
	};
									
	protected String name;
	protected Date date;
	protected MEASUREMENT_TYPE type;
	
	protected List<MetricValue> metricValues;
	
	protected List<Measurement> innerMeasurements;
	
	
	
	public Measurement() {
		super();
		this.metricValues = new ArrayList<MetricValue>();
		this.innerMeasurements = new ArrayList<Measurement>();
	}
	
	public Measurement(String name, Date date, MEASUREMENT_TYPE type,
			List<MetricValue> metricValues, List<Measurement> innerMeasurements) {
		super();
		this.name = name;
		this.date = date;
		this.type = type;
		this.metricValues = new ArrayList<MetricValue>();
		this.innerMeasurements = new ArrayList<Measurement>();
		if (metricValues != null) {
			this.metricValues = metricValues;	
		}
		if (innerMeasurements != null) {
			this.innerMeasurements = innerMeasurements;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public MEASUREMENT_TYPE getType() {
		return type;
	}
	public void setType(MEASUREMENT_TYPE type) {
		this.type = type;
	}
	
	@XmlElementWrapper(name="metricsValues")
	@XmlElement(name="metricValue")
	public List<MetricValue> getMetricValues() {
		return metricValues;
	}
	public void setMetricValues(List<MetricValue> metricValues) {
		this.metricValues = metricValues;
	}
	
	@XmlElementWrapper(name="innerMeasurements")
	@XmlElement(name="measurement")
	public List<Measurement> getInnerMeasurements() {
		return innerMeasurements;
	}
	public void setInnerMeasurements(List<Measurement> innerMeasurements) {
		this.innerMeasurements = innerMeasurements;
	}
	@Override
	public final int hashCode() {
		return this.getName().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return this.getName().equals(((Measurement) obj).getName());
	}
	@Override
	public String toString() {
		return "[Measurement. Name: " 
				+ this.getName()
				+ ", Type: " + this.getType().toString()
				+ ", Metrics Values: "
				+ " \r\n" + this.metricValues
				+ ", Inner measurements: " 
				+ "\r\n" + this.innerMeasurements
				+ "]";
	}
	@Override
	public int compareTo(Measurement o) {
		return this.getName().compareTo(o.getName());
	}
	
	/**
	 * Convenience method to return a metric value, based on its name.
	 * @param metricName The metric name
	 * @return Metric value or null, if it does not exist.
	 */
	public MetricValue getMetricValue(String metricName) {
		MetricValue mv = new MetricValue();
		mv.setName(metricName);
		int indx = this.getMetricValues().indexOf(mv);
		if (indx >= 0) {
			mv = this.getMetricValues().get(indx);
		}
		else {
			mv = null;
		}
		return mv;
	}

}