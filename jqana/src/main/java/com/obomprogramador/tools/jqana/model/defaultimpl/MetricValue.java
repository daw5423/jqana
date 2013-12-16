package com.obomprogramador.tools.jqana.model.defaultimpl;

public class MetricValue implements Comparable<MetricValue> {

	protected String name;
	protected double value;
	protected int    qtdElements;
	protected boolean violated;
	
	public MetricValue() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	
	
	@Override
	public String toString() {
		return "MetricValue [name=" + name + ", value=" + value
				+ ", qtdElements=" + qtdElements + ", violated=" + violated
				+ "]";
	}

	public int getQtdElements() {
		return qtdElements;
	}

	public void setQtdElements(int qtdElements) {
		this.qtdElements = qtdElements;
	}

	public boolean isViolated() {
		return violated;
	}

	public void setViolated(boolean violated) {
		this.violated = violated;
	}
	
	@Override
	public int hashCode() {
		return 31 + this.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getName().equals(((MetricValue) obj).getName());
	}

	@Override
	public int compareTo(MetricValue other) {
		return this.getName().compareTo(other.getName());
	}
	
	

}
