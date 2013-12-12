package com.obomprogramador.tools.jacana;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.context.GlobalConstants;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.defaultimpl.ClassMeasurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.defaultimpl.MethodMeasurement;
import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;

public class TestCyclomaticComplexity {

	@Test
	public void test() {
		Metric metric = new DefaultMetric();
		metric.setMetricName(CYCLOMATIC_COMPLEXITY);
		MaxLimitVerificationAlgorithm mlva = new MaxLimitVerificationAlgorithm(5);
		metric.setVerificationAlgorithm(mlva);
		Context context = new Context();
		context.setValidMetrics(new ArrayList<Metric>());
		context.getValidMetrics().add(metric);
		String uri = getSource("abc/Teste2.java"); 
		
		Parser parser = new CyclomaticComplexityParser(context);
		Measurement mt = parser.parse( null, uri);
		assertTrue(mt != null);
		assertTrue(mt.getMetricValue() != 0);
		
		System.out.println("Package: " + mt.getPackageName() + ", class: " + mt.getClassName() 
				+ ", metric: " + mt.getMetric().getMetricName()
				+ ", value: " + mt.getMetricValue() + ", violated: " + mt.isViolated());
		printMembers(4, mt);
				
	}
	
	public void printMembers(int identation, Measurement mt) {
		for (Measurement m2 : mt.getInnerMeasurements()) {
			String line = ""; 
			line += StringUtils.leftPad(line,identation);
			if (m2 instanceof ClassMeasurement) {
				line += "Inner Class: " + m2.getClassName();
			}
			else if (m2 instanceof MethodMeasurement) {
					line += "Method: " + m2.getMethodName();
			}
			
			line += ", Metric: "
				+ m2.getMetric().getMetricName()
				+ ", value: "
				+ m2.getMetricValue()
				+ ", violated: "
				+ m2.isViolated();
			System.out.println(line);
			printMembers(identation + 4, m2);
		}
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

}