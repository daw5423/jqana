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

import com.obomprogramador.tools.jqana.context.GlobalConstants;

/**
 * This represents a measurement taken from source code. It may have some inner measurements.
 * @author Cleuton Sampaio
 *
 */
public interface Measurement {
	
	/**
	 * Metric getter.
	 * @return metric associated with this measurement.
	 */
	public abstract Metric getMetric();
	/**
	 * Metric setter.
	 * @param metric the metric associated with this measurement.
	 */
	public abstract void setMetric(Metric metric);
	/**
	 * Package name getter.
	 * @return the package name of the class under analysis.
	 */
	public abstract String getPackageName();
	/**
	 * Package name setter.
	 * @param packageName The package name of the class under analysis.
	 */
	public abstract void setPackageName(String packageName);
	/**
	 * Class name getter.
	 * @return the name of the class under analysis.
	 */
	public abstract String getClassName();
	/**
	 * Class name setter.
	 * @param className the name of the class under analysis.
	 */
	public abstract void setClassName(String className);
	/**
	 * Method name getter.
	 * @return the name of the method under analysis.
	 */
	public abstract String getMethodName();
	/**
	 * Method name setter.
	 * @param methodName the name of the method under analysis.
	 */
	public abstract void setMethodName(String methodName);
	/**
	 * Metric value getter.
	 * @return current observed value of the metric under analysis.
	 */
	public abstract double getMetricValue();
	/**
	 * Metric value setter.
	 * @param value of the metric under analysis.
	 */
	public abstract void setMetricValue(double value);
	/**
	 * Metric violation condition getter.
	 * @return true if the metric is considered violated, according to its verification algorithm.
	 */
	public abstract boolean isViolated();
	/**
	 * Metric violation condition getter.
	 * @param violated true if the metric is considered violated, according to its verification algorithm.
	 */
	public abstract void setViolated(boolean violated);
	/**
	 * Measurement type getter, according to the global constants.
	 * @see GlobalConstants.MEASUREMENT_TYPE
	 * @return GlobalConstants.MEASUREMENT_TYPE.ordinal()
	 */
	public abstract int getMeasurementType();
	/**
	 * Measurement type setter, according to the global constants.
	 * @see GlobalConstants.MEASUREMENT_TYPE
	 * @param type GlobalConstants.MEASUREMENT_TYPE.ordinal()
	 */
	public abstract void setMeasurementType(int type);
	/**
	 * Inner measurements getter. A measurement can have inner measurements, for example,
	 * a Class can have methods and inner classes measurements.
	 * @return The inner measurements' list.
	 */
	public abstract List<Measurement> getInnerMeasurements();
	/**
	 * Inner measurements setter. A measurement can have inner measurements, for example,
	 * a Class can have methods and inner classes measurements.
	 * @param The inner measurements' list.
	 */
	public abstract void setInnerMeasurements(List<Measurement> measurements);

}