package com.obomprogramador.tools.jqana.model;

import java.util.List;


public interface Measurement {
	
	public abstract Metric getMetric();
	
	public abstract void setMetric(Metric metric);

	public abstract String getPackageName();

	public abstract void setPackageName(String packageName);

	public abstract String getClassName();

	public abstract void setClassName(String className);

	public abstract String getMethodName();

	public abstract void setMethodName(String methodName);

	public abstract double getMetricValue();

	public abstract void setMetricValue(double value);

	public abstract boolean isViolated();

	public abstract void setViolated(boolean violated);
	
	public abstract int getMeasurementType();
	
	public abstract void setMeasurementType(int type);
	
	public abstract List<Measurement> getInnerMeasurements();
	
	public abstract void setInnerMeasurements(List<Measurement> measurements);

}