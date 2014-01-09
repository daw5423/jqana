package com.obomprogramador.tools.jacana;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Test;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;
import com.obomprogramador.tools.jqana.model.defaultimpl.RetriveTestResults;
import com.obomprogramador.tools.jqana.parsers.Lcom4Parser;
import com.obomprogramador.tools.jqana.parsers.RfcBcelParser;
import com.obomprogramador.tools.jqana.parsers.RfcParser;

public class TestRfc {
	
	private Context context;

	
	public String[][] testClasses;
	
	@Test
	public void testAllClassesRFC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		context = new Context();
		testClasses = RetriveTestResults.getResults(context);
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		for (int x=0; x<testClasses.length; x++) {
			
			String uri = this.getClass().getClassLoader().getResource(testClasses[x][0] + "/" + testClasses[x][1] + ".class").getFile();
			Measurement packageMeasurement = new Measurement();
			packageMeasurement.setName(testClasses[x][0]);
			packageMeasurement.setType(MEASUREMENT_TYPE.PACKAGE_MEASUREMENT);
			Parser parser = new RfcBcelParser(packageMeasurement, context);
			Measurement mt = parser.parse( uri, null);
			MetricValue mv = packageMeasurement.getMetricValue(context.getBundle().getString("metric.rfc.name"));
			assertTrue(mt != null);
			double diffCC = Math.abs(mv.getValue() - Double.parseDouble(testClasses[x][4]));
			System.out.println("Testing class: " + testClasses[x][0] + "." + testClasses[x][1] + " RFC: " + mv.getValue());
			assertTrue(diffCC < 0.5);

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