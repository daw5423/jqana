package com.obomprogramador.tools.jqana.model;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaLexer;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;
import com.obomprogramador.tools.jqana.parsers.RfcListener;

public abstract class AbstractMetricParser implements Parser {

	protected Context context;
	protected Measurement measurement;
	protected Measurement packageMeasurement;
	protected Logger logger;
	protected Metric metric;
	protected MetricValue metricValue;
	protected JavaBaseListener listener;
	protected String metricResourceId;
	
	
	public AbstractMetricParser(Measurement packageMeasurement, Context context,String metricResourceId) {
		logger = LoggerFactory.getLogger(this.getClass());
		this.context = context;
		this.packageMeasurement = packageMeasurement;
		this.metricResourceId = metricResourceId;
		this.metric = context.getCurrentMetric(context.getBundle().getString(metricResourceId));
		if (this.metric == null) {
			throw new IllegalArgumentException("Context is not valid. Metric is null.");
		}
	}

	@Override
	public String getParserName() {
		return this.metric.getMetricName();
	}

	@Override
	public Measurement parse(Class<?> clazz, String sourceCode) {
		this.measurement = new Measurement(); // Class name will be set inside listener.
		this.measurement.setType(MEASUREMENT_TYPE.CLASS_MEASUREMENT);
		this.metricValue = new MetricValue();
		this.metricValue.setName(this.metric.getMetricName());
		this.measurement.getMetricValues().add(this.metricValue);
		
		JavaLexer lexer;
		try {
			lexer = new JavaLexer(new ANTLRInputStream(sourceCode));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			JavaParser p = new JavaParser(tokens);
	        ParseTree tree = (ParseTree)(p.compilationUnit()); 
	        ParseTreeWalker walker = new ParseTreeWalker();
	        
	        // Template method hook:
	        this.listener = getListener(p);
	        
	        walker.walk(this.listener, tree); 
	        logger.debug("**** " + this.metric.getMetricName() + ": " + this.measurement.toString());

	        // Template method hook:
	        afterProcessing();
	        
	        updatePackageMeasurement();
		} catch (Exception e) {
			context.getErrors().push(e.getMessage());
			logger.error(e.getMessage());
		}
            
		return this.measurement;
	}
	
	/**
	 * This is a template method hook. It must return a listener instance.
	 * @param p
	 * @return
	 */
	public JavaBaseListener getListener(JavaParser p) {
		
		return null;
	}

	/**
	 * This is a template method hook. 
	 * What do you want to do after parsing the source file?
	 */
	public void afterProcessing() {
		
	}
	
	/* (non-javadoc)
	 * 
	 * This is a template method that updates package measurement.
	 */
	protected void updatePackageMeasurement() {
		
		Measurement classMeasurement = null;
		MetricValue mv = null;
		MetricValue packageMv = null;
		
		// 1 - Add ths chass' measurements to the package's measurements collection:
		
		int indx = this.packageMeasurement.getInnerMeasurements().indexOf(this.measurement);
		if (indx >= 0) {
			// Collection of inner measurements already has a measurement of this class. Ok.
			classMeasurement = this.packageMeasurement.getInnerMeasurements().get(indx);
		}
		else {
			// It is the first measurement of this class:
			classMeasurement = this.measurement;
			this.packageMeasurement.getInnerMeasurements().add(this.measurement);
		}
		
		// 2 - Add this class' metric value to package's metric values, calculating the average
		
		mv = this.metricValue;
		if (classMeasurement.getMetricValues().contains(mv)) {
			classMeasurement.getMetricValues().remove(mv);
		}
		classMeasurement.getMetricValues().add(mv);
		
		if (this.packageMeasurement.getMetricValues().contains(mv)) {
			// This package already contains a CC metric value, so, lets add to it:
			int mvIndx = this.packageMeasurement.getMetricValues().indexOf(mv);
			packageMv = this.packageMeasurement.getMetricValues().get(mvIndx);
			
			updatePackageMetrics(packageMv, mv);
			
			
			// Package average is calculated after all its classes.
			
		}
		else {
			packageMv = new MetricValue();
			packageMv.setName(mv.getName());
			packageMv.setValue(mv.getValue());
			packageMv.setQtdElements(1);
			packageMv.setViolated(mv.isViolated());
			this.packageMeasurement.getMetricValues().add(packageMv);
		}
	}

	/**
	 * Part of the template method "updatePackageMeasurement()" that aggregates the metric value.
	 * @param packageMv
	 */
	public void updatePackageMetrics(MetricValue packageMv, MetricValue mv) {
		packageMv.setValue(packageMv.getValue() + mv.getValue());
		packageMv.setQtdElements(packageMv.getQtdElements() + 1);
		if (mv.isViolated()) {
			packageMv.setViolated(true);
		}
	}

	private MetricValue getMetricValueForClass(Measurement classMeasurement) {
		MetricValue mv = null;
		mv = classMeasurement.getMetricValue(this.metric.getMetricName());
		if (mv == null) {
			mv = new MetricValue();
			mv.setName(this.metric.getMetricName());
			classMeasurement.getMetricValues().add(mv);
		}
		return mv;
	}


}
