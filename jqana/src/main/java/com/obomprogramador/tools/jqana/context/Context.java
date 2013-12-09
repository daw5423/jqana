package com.obomprogramador.tools.jqana.context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.obomprogramador.tools.jqana.model.Metric;

public class Context {

	private String currentClassUri;
	private Deque<String> errors;
	private List<Metric> validMetrics;
	
	public Context() {
		super();
		this.errors = new ArrayDeque<String>();
	}
	
	public List<Metric> getValidMetrics() {
		return validMetrics;
	}

	public void setValidMetrics(List<Metric> validMetrics) {
		this.validMetrics = validMetrics;
	}

	public Deque<String> getErrors() {
		return errors;
	}

	public void setErrors(Deque<String> errors) {
		this.errors = errors;
	}

	
	public String getCurrentClassUri() {
		return currentClassUri;
	}

	public void setCurrentClassUri(String currentClassUri) {
		this.currentClassUri = currentClassUri;
	}

	
}
