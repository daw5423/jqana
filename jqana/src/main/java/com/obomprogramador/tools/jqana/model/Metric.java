package com.obomprogramador.tools.jqana.model;

public interface Metric {
	public String 	getMetricName();
	public void     setMetricName(String name);
	public String   getMetricMessage();
	public void     setMetricMessage(String message);
	public LimitVerificationAlgorithm getVerificationAlgorithm();
	public void     setVerificationAlgorithm(LimitVerificationAlgorithm lva);
}
