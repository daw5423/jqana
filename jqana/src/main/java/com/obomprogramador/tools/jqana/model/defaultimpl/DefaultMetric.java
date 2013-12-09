package com.obomprogramador.tools.jqana.model.defaultimpl;

import com.obomprogramador.tools.jqana.model.LimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.Metric;

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
