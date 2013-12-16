package com.obomprogramador.tools.jacana;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;
import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;
import com.obomprogramador.tools.jqana.parsers.Lcom4Parser;

public class TestLcom4 {

	private Context context;

	@Test
	public void testLcom4Gt1() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		String uri = getSource("unit-test-sources/abc/TesteLcomMaiorQueUm.java"); 
		Measurement packageMeasurement = new Measurement();
		packageMeasurement.setName("abc");
		packageMeasurement.setType(MEASUREMENT_TYPE.PACKAGE_MEASUREMENT);
		Parser parser = new Lcom4Parser(packageMeasurement, context);
		Measurement mt = parser.parse( null, uri);
		assertTrue(mt != null);
		MetricValue mv = packageMeasurement.getMetricValue(context.getBundle().getString("metric.lcom4.name"));
		assertTrue(mv != null);
		assertTrue(mv.getValue() != 0);
		printPackage(4,packageMeasurement);
	}
	
	private String getSource(String string) {
		String sourceUri = this.getClass().getClassLoader().getResource(string).getFile();
		String sourceCode = null;
		sourceCode = readFile(sourceUri);
		return sourceCode;
	}
	
	private String readFile(String fileName)  {
		BufferedReader br = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	    	br = new BufferedReader(new FileReader(fileName));
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return sb.toString();
	}

	@Test
	public void testLcom4Eq1() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		String uri = getSource("unit-test-sources/abc/TesteLCom4Um.java"); 
		Measurement packageMeasurement = new Measurement();
		packageMeasurement.setName("abc");
		packageMeasurement.setType(MEASUREMENT_TYPE.PACKAGE_MEASUREMENT);
		Parser parser = new Lcom4Parser(packageMeasurement, context);
		Measurement mt = parser.parse( null, uri);
		assertTrue(mt != null);
		MetricValue mv = packageMeasurement.getMetricValue(context.getBundle().getString("metric.lcom4.name"));
		assertTrue(mv != null);
		assertTrue(mv.getValue() == 1);
		printPackage(4,packageMeasurement);
	}

	
	public void printPackage(int identation, Measurement mt) {
		String line = ""; 
		line += StringUtils.leftPad(line,identation);
		MetricValue mv = mt.getMetricValue(context.getBundle().getString("metric.lcom4.name"));
		switch (mt.getType()) {
		case PACKAGE_MEASUREMENT:
			line += "Package: " + mt.getName(); 
			line += ", Metric: "
					+ mv.getName()
					+ ", value (avg): "
					+ mv.getValue();
			break;
		case CLASS_MEASUREMENT:
			line += "Class: " + mt.getName();
			line += ", Metric: "
					+ mv.getName()
					+ ", value (avg): "
					+ mv.getValue();
			break;
		case METHOD_MEASUREMENT:
			line += "Method: " + mt.getName(); 
			line += ", Metric: "
					+ mv.getName()
					+ ", value: "
					+ mv.getValue();
		}
		
		line += ", violated: "
			+ mv.isViolated();
		System.out.println(line);
		for (Measurement m2 : mt.getInnerMeasurements()) {
			printPackage(identation + 4, m2);
		}
	}
}