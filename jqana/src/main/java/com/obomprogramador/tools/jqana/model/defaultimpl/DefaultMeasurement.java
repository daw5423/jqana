package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import com.obomprogramador.tools.jqana.context.GlobalConstants.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;

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
