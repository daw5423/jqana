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

import java.util.ArrayList;
import java.util.List;

import com.obomprogramador.tools.jqana.context.GlobalConstants.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
/**
 * Default Measurement implementation.
 * @see Measurement
 * @author Cleuton Sampaio
 *
 */
public class DefaultMeasurement implements Measurement {

	private Metric metric;
	private String packageName;
	private String className;
	private String methodName;
	private double metricValue;
	private boolean violated;
	private List<Measurement> innerMeasurements;
	private MEASUREMENT_TYPE type;
	
	
	@Override
	public String toString() {
		return "[Measurement: \r\n"
				+ this.metric
				+ "\r\n; packageName="
				+ this.packageName
				+ "; className="
				+ this.className
				+ "; methodName="
				+ this.methodName
				+ "; metricValue="
				+ this.metricValue
				+ "; violated="
				+ this.violated
				+ "[InnerMeasurements = \r\n" 
				+ this.getInnerMeasurements().toString()
				+ "\r\n]]";
	}

	public DefaultMeasurement() {
		super();
		this.innerMeasurements = new ArrayList<Measurement>();
	}
	
	@Override
	public List<Measurement> getInnerMeasurements() {
		return innerMeasurements;
	}

	@Override
	public void setInnerMeasurements(List<Measurement> innerMeasurements) {
		this.innerMeasurements = innerMeasurements;
	}

	@Override
	public Metric getMetric() {
		return this.metric;
	}

	@Override
	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	@Override
	public String getPackageName() {
		return this.packageName;
	}

	@Override
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

	@Override
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String getMethodName() {
		return this.methodName;
	}

	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public double getMetricValue() {
		return this.metricValue;
	}

	@Override
	public void setMetricValue(double value) {
		this.metricValue = value;
	}

	@Override
	public boolean isViolated() {
		return this.violated;
	}

	@Override
	public void setViolated(boolean violated) {
		this.violated = violated;
	}

	@Override
	public int getMeasurementType() {
		return this.type.ordinal();
	}

	@Override
	public void setMeasurementType(int type) {
		this.type = MEASUREMENT_TYPE.values()[type];
	}

}
