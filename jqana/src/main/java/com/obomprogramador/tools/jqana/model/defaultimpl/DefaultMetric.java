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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.obomprogramador.tools.jqana.model.LimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.Metric;

/**
 * Default Metric implementation.
 * 
 * @see Metric
 * @author Cleuton Sampaio.
 * 
 */
@XmlRootElement
public class DefaultMetric extends Metric implements Comparable<Metric> {

    private String metricName;
    private String message;
    private LimitVerificationAlgorithm lva;

    /**
     * Default constructor.
     */
    public DefaultMetric() {
        super();
    }

    /**
     * Constructor with fields.
     * @param metricName String the metric name.
     * @param message String the metric default message. 
     */
    public DefaultMetric(String metricName, String message) {
        super();
        this.metricName = metricName;
        this.message = message;
    }

    /**
     * Getter form message.
     * @return String the metric's message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message.
     * @param message String the metric's message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Limit verification algorithm to be used.
     * @return LimitVerificationAlgorithm the algorithm instance.
     */
    @XmlTransient
    public LimitVerificationAlgorithm getLva() {
        return lva;
    }

    /**
     * Setter for LVA.
     * @param lva LimitVerificationAlgorithm. 
     */
    public void setLva(LimitVerificationAlgorithm lva) {
        this.lva = lva;
    }

    /**
     * Object to String. 
     * @return String the string representation.
     */
    @Override
    public String toString() {
        return "[Metric:  metricName=" + this.metricName
                + "; limitVerificationAlgorithm="
                + this.lva.getClass().getSimpleName() + "]";
    }

    /**
     * Getter for metric name.
     * @return String the metric name.
     */
    @Override
    public String getMetricName() {
        return this.metricName;
    }

    /**
     * Setter for metric name.
     * @param name String the metric name.
     */
    @Override
    public void setMetricName(String name) {
        this.metricName = name;
    }

    /**
     * Getter for metric message.
     * @return the metric message.
     */
    @Override
    public String getMetricMessage() {
        return this.message;
    }

    /**
     * Setter for metric message.
     * @param message String the metric message.
     */
    @Override
    public void setMetricMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the LimitVerificationAlgorithm.
     * @return LimitVerificationAlgorithm the lva.
     */
    @XmlTransient
    @Override
    public LimitVerificationAlgorithm getVerificationAlgorithm() {
        return this.lva;
    }

    /**
     * Setter for the LimitVerificationAlgorithm.
     * @param lva LimitVerificationAlgorithm.
     */
    @Override
    public void setVerificationAlgorithm(LimitVerificationAlgorithm lva) {
        this.lva = lva;
    }

    /**
     * Object hashcode.
     * @return int the calculated HashCode.
     */
    @Override
    public int hashCode() {
        return this.getMetricName().hashCode();
    }

    /**
     * Object equals.
     * @param obj Object the object to compare with.
     * @return boolean whether this is the same object. 
     */
    @Override
    public boolean equals(Object obj) {
        return this.getMetricName().equalsIgnoreCase(
                ((Metric) obj).getMetricName());
    }

    /**
     * Object comparison.
     * @param o Metric the Metric to compare with.
     * @return int Comparison result.  
     */
    @Override
    public int compareTo(Metric o) {
        return this.getMetricName().compareToIgnoreCase(o.getMetricName());
    }

}
