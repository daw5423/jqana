package com.obomprogramador.tools.jacana;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultProjectProcessor;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXmlGenerator;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;
import com.obomprogramador.tools.jqana.model.defaultimpl.RetriveTestResults;

public class TestUsingXmlGenerator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String rootTestResources = "unit-test-sources/java";

	private Measurement project;
	private Context context;
	public String[][] testClasses;
	
	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			context = new Context();
			testClasses = RetriveTestResults.getResults(context);
			DefaultProjectProcessor dpp = new DefaultProjectProcessor(new Context());
			File sourceDir = new File(this.getClass().getClassLoader().getResource(rootTestResources).toURI());
			File objectDir = sourceDir.getParentFile().getParentFile();
			this.project = dpp.process("Teste", sourceDir, objectDir);
			DefaultXmlGenerator generator = new DefaultXmlGenerator();
			Document report = generator.serialize(this.project);
			assertTrue(verifyResult(report));
			logger.debug("Test OK! XML: " + generator.xml2String(report,true));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
	}

	private boolean verifyResult(Document report) throws JAXBException {
		boolean result = true;
		JAXBContext jcontext = JAXBContext.newInstance(Measurement.class);
		Unmarshaller m = jcontext.createUnmarshaller();
		Measurement projectMeasurement = (Measurement) m.unmarshal(report);
		List<Measurement> packages = projectMeasurement.getInnerMeasurements();
		for (int x=0; x<testClasses.length; x++) {
			Measurement packageM = getMeasurement(packages,testClasses[x][0]);
			if (packageM == null) {
				System.out.println("*** ERROR Package not found: " + testClasses[x][0]);
				result = false;
				break;
			}
			Measurement classM = getMeasurement(packageM.getInnerMeasurements(),testClasses[x][1]);
			if (classM == null) {
				System.out.println("*** ERROR Class not found: " + testClasses[x][1]);
				result = false;
				break;
			}
			

			MetricValue ccMetric = classM.getMetricValue(context.getBundle().getString("metric.cc.name"));
			if (ccMetric == null) {
				System.out.println("*** ERROR ccMetric is missing. Class: " + testClasses[x][1]);
				result = false;
				break;
			}
			MetricValue lcom4Metric = classM.getMetricValue(context.getBundle().getString("metric.lcom4.name"));
			if (ccMetric == null) {
				System.out.println("*** ERROR lcom4Metric is missing. Class: " + testClasses[x][1]);
				result = false;
				break;
			}	
			MetricValue rfcMetric = classM.getMetricValue(context.getBundle().getString("metric.rfc.name"));
			if (ccMetric == null) {
				System.out.println("*** ERROR rfcMetric is missing. Class: " + testClasses[x][1]);
				result = false;
				break;
			}			
			String value = "";
			try {
				value = "cc";
				double ccValue = Double.parseDouble(testClasses[x][2]);
				value = "lcom4";
				double lcom4Value = Double.parseDouble(testClasses[x][3]);
				value = "rfc";
				double rfcValue = Double.parseDouble(testClasses[x][4]);
				if (Math.abs(ccValue - ccMetric.getValue()) > 0.5) {
					System.out.println("*** ERROR metric CC value for class: " + testClasses[x][1] + " expected: " + ccValue + " found: " + ccMetric.getValue());
					result = false;
				}
				if (Math.abs(lcom4Value - lcom4Metric.getValue()) > 0.5) {
					System.out.println("*** ERROR metric lcom4 value for class: " + testClasses[x][1] + " expected: " + lcom4Value + " found: " + lcom4Metric.getValue());
					result = false;
				}
				if (Math.abs(rfcValue - rfcMetric.getValue()) > 0.5) {
					System.out.println("*** ERROR metric rfc value for class: " + testClasses[x][1] + " expected: " + rfcValue + " found: " + rfcMetric.getValue());
					result = false;
				}				
				
			}
			catch (NumberFormatException nfe) {
				System.out.println("*** ERROR invalid metric value for class: " + testClasses[x][1] + " value: " + value);
				result = false;
			}
		}
		return result;
	}

	private Measurement getMeasurement(List<Measurement> measurements, String name) {
		Measurement m = new Measurement();
		m.setName(name);
		int indx = measurements.indexOf(m);
		if (indx < 0) {
			m = null;
		}
		else {
			m = measurements.get(indx);
		}
		return m;
	}


	

}