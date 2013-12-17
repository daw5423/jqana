package com.obomprogramador.tools.jacana;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXmlGenerator;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;
import com.obomprogramador.tools.jqana.parsers.Lcom4Parser;
import com.obomprogramador.tools.jqana.parsers.RfcParser;

public class TestUsingXmlGenerator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String rootTestResources = "unit-test-sources";
	private List<Measurement> measurements;
	private String currentFolderName;
	private Measurement project;
	
	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		measurements = new ArrayList<Measurement>();
		try {
			processPackages(this.getResourceListing(this.getClass(), rootTestResources));
			DefaultXmlGenerator generator = new DefaultXmlGenerator();
			Document report = generator.serialize(this.project);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void processPackages(String[] strings) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Measurement packageMeasurement = null;
		project = new Measurement();
		project.setName("unit-test-sources");
		project.setType(MEASUREMENT_TYPE.PROJECT_MEASUREMENT);
		if (strings != null && strings.length > 0) {
			for (int x=0; x<strings.length; x++) {
				packageMeasurement = new Measurement();
				packageMeasurement.setName(strings[x]);
				packageMeasurement.setType(MEASUREMENT_TYPE.PACKAGE_MEASUREMENT);
				processSingleFolder(packageMeasurement,strings[x]);
				project.getInnerMeasurements().add(packageMeasurement);
			}
		}
		
	}
	
	private void processSingleFolder(Measurement packageMeasurement, String folderName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			currentFolderName = rootTestResources + "/" + folderName;
			String [] files = this.getResourceListing(this.getClass(), currentFolderName);
			for (int x=0; x<files.length; x++) {
				processMetrics(packageMeasurement, currentFolderName + "/" + files[x]);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processMetrics(Measurement packageMeasurement, String sourceFile) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		logger.debug("Source file: " + sourceFile);
		String sourceCode = this.getSource(sourceFile);
		processCyclomaticMetric(sourceCode, packageMeasurement);
		processLcom4Metric(sourceCode, packageMeasurement);
		processRfcMetric(sourceCode, packageMeasurement);
	}

	private void processRfcMetric(String sourceFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Context context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new RfcParser(packageMeasurement, context);
		Measurement mt = parser.parse( null, sourceFile);
		assertTrue(mt != null);
		
	}

	private String getSource(String sourceFile) {
		BufferedReader br = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	    	br = new BufferedReader(new InputStreamReader(getStream(sourceFile),"UTF-8"));
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

	private InputStream getStream(String sourceFile) {
		return this.getClass().getClassLoader().getResourceAsStream(sourceFile);
	}

	private void processLcom4Metric(String sourceFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Context context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new Lcom4Parser(packageMeasurement, context);
		Measurement mt = parser.parse( null, sourceFile);
		assertTrue(mt != null);
		
	}

	private void processCyclomaticMetric(String sourceFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Context context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new CyclomaticComplexityParser(packageMeasurement, context);
		Measurement mt = parser.parse( null, sourceFile);
		MetricValue mv = packageMeasurement.getMetricValue(context.getBundle().getString("metric.cc.name"));
		assertTrue(mt != null);
		
	}

	/**
	   * List directory contents for a resource folder. Not recursive.
	   * This is basically a brute-force implementation.
	   * Works for regular files and also JARs.
	   * 
	   * @author Greg Briggs
	   * @param clazz Any java class that lives in the same place as the resources you want.
	   * @param path Should end with "/", but not start with one.
	   * @return Just the name of each member item, not the full paths.
	   * @throws URISyntaxException 
	   * @throws IOException 
	   */
	  String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
	      URL dirURL = clazz.getClassLoader().getResource(path);
	      if (dirURL != null && dirURL.getProtocol().equals("file")) {
	        /* A file path: easy enough */
	        return new File(dirURL.toURI()).list();
	      } 

	      if (dirURL == null) {
	        /* 
	         * In case of a jar file, we can't actually find a directory.
	         * Have to assume the same jar as clazz.
	         */
	        String me = clazz.getName().replace(".", "/")+".class";
	        dirURL = clazz.getClassLoader().getResource(me);
	      }
	      
	      if (dirURL.getProtocol().equals("jar")) {
	        /* A JAR path */
	        String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
	        JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
	        Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
	        Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
	        while(entries.hasMoreElements()) {
	          String name = entries.nextElement().getName();
	          if (name.startsWith(path)) { //filter according to the path
	            String entry = name.substring(path.length());
	            int checkSubdir = entry.indexOf("/");
	            if (checkSubdir >= 0) {
	              // if it is a subdirectory, we just return the directory name
	              entry = entry.substring(0, checkSubdir);
	            }
	            result.add(entry);
	          }
	        }
	        return result.toArray(new String[result.size()]);
	      } 
	        
	      throw new UnsupportedOperationException("Cannot list files for URL "+dirURL);
	  }

}
