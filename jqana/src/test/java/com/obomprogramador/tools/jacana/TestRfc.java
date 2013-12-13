package com.obomprogramador.tools.jacana;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.parsers.Lcom4Parser;
import com.obomprogramador.tools.jqana.parsers.RfcParser;

public class TestRfc {

	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Metric metric = new DefaultMetric();
		//metric.setMetricName(RFC);
		MaxLimitVerificationAlgorithm mlva = new MaxLimitVerificationAlgorithm(50);
		metric.setVerificationAlgorithm(mlva);
		Context context = new Context();
		context.setValidMetrics(new ArrayList<Metric>());
		context.getValidMetrics().add(metric);
		String uri = getSource("abc/TesteRfc.java"); 

		Parser parser = new RfcParser(context);
		Measurement mt = parser.parse( null, uri);
		assertTrue(mt != null);
		//assertTrue(mt.getMetricValue() != 0);
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