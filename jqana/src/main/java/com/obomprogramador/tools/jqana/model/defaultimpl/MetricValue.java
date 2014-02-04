package com.obomprogramador.tools.jqana.model.defaultimpl;

/**
 * Class to hold a metric value. 
 * @author Cleuton Sampaio
 *
 */
public class MetricValue implements Comparable<MetricValue> {

    protected String name;
    protected double value;
    protected int qtdElements;
    protected boolean violated;
    private static final int PRIME = 31;

    /**
     * Default constructor.
     */
    public MetricValue() {
        super();
    }

    /**
     * Getter for name.
     * @return String the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name String the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the value. 
     * @return double the value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Setter for the value.
     * @param value double The value.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Object to string.
     * @return String the textual representation.
     */
    @Override
    public String toString() {
        return "MetricValue [name=" + name + ", value=" + value
                + ", qtdElements=" + qtdElements + ", violated=" + violated
                + "]";
    }

    /**
     * Getter for quantity of elements.
     * @return int QTDE O ELEMENTS.
     */
    public int getQtdElements() {
        return qtdElements;
    }

    /**
     * Setter for quantity of elements.
     * @param qtdElements int QTDE of Elements.
     */
    public void setQtdElements(int qtdElements) {
        this.qtdElements = qtdElements;
    }

    /**
     * Getter for metric violation.
     * @return boolean whether is considered in violation or not.
     */
    public boolean isViolated() {
        return violated;
    }

    /**
     * Setter for metric violation.
     * @param violated boolean whether is violated (true) or not.
     */
    public void setViolated(boolean violated) {
        this.violated = violated;
    }

    /**
     * Object hash code.
     * @return int the Calculated hashcode.
     */
    @Override
    public int hashCode() {
        return PRIME + this.getName().hashCode();
    }

    /**
     * Object equals.
     * @param obj Object the object to compare with.
     * @return boolean True if they are the same object.
     */
    @Override
    public boolean equals(Object obj) {
        return this.getName().equals(((MetricValue) obj).getName());
    }

    /**
     * Object comparison.
     * @param other MetricValue the MetricValue to compare with.
     * @return int the comparison result..
     */
    @Override
    public int compareTo(MetricValue other) {
        return this.getName().compareTo(other.getName());
    }

}
