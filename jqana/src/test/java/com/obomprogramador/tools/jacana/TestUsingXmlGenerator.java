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
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;

public class TestUsingXmlGenerator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String rootTestResources = "unit-test-sources";
	private List<Measurement> measurements;
	private String currentFolderName;
	@Test
	public void test() {
		measurements = new ArrayList<Measurement>();
		try {
			processPackages(this.getResourceListing(this.getClass(), rootTestResources));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(measurements.toString());
	}

	private void processPackages(String[] strings) {
		if (strings != null && strings.length > 0) {
			for (int x=0; x<strings.length; x++) {
				processSingleFolder(strings[x]);
			}
		}

		
	}
	
	private void processSingleFolder(String folderName) {
		try {
			currentFolderName = rootTestResources + "/" + folderName;
			String [] files = this.getResourceListing(this.getClass(), currentFolderName);
			for (int x=0; x<files.length; x++) {
				processMetrics(files[x]);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processMetrics(String sourceFile) {
		logger.debug("Source file: " + sourceFile);
		
		measurements.add(getCyclomaticMetric(sourceFile));
		measurements.add(getLcom4Value(sourceFile));
		measurements.add(getRfc(sourceFile));
		
	}

	private Measurement getRfc(String sourceFile) {
		Metric metric = new DefaultMetric();
		metric.setMetricName(RFC);
		MaxLimitVerificationAlgorithm mlva = new MaxLimitVerificationAlgorithm(5);
		metric.setVerificationAlgorithm(mlva);
		Context context = new Context();
		context.setValidMetrics(new ArrayList<Metric>());
		context.getValidMetrics().add(metric);
		String source = getSource(sourceFile); 
		
		Parser parser = new CyclomaticComplexityParser(context);
		Measurement mt = parser.parse( null, source);
		return mt;
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
		return this.getClass().getClassLoader().getResourceAsStream(currentFolderName + "/" + sourceFile);
	}

	private Measurement getLcom4Value(String sourceFile) {
		Metric metric = new DefaultMetric();
		metric.setMetricName(LCOM4);
		MaxLimitVerificationAlgorithm mlva = new MaxLimitVerificationAlgorithm(5);
		metric.setVerificationAlgorithm(mlva);
		Context context = new Context();
		context.setValidMetrics(new ArrayList<Metric>());
		context.getValidMetrics().add(metric);
		String source = getSource(sourceFile); 
		
		Parser parser = new CyclomaticComplexityParser(context);
		Measurement mt = parser.parse( null, source);
		return mt;
	}

	private Measurement getCyclomaticMetric(String sourceFile) {
		Metric metric = new DefaultMetric();
		metric.setMetricName(CYCLOMATIC_COMPLEXITY);
		MaxLimitVerificationAlgorithm mlva = new MaxLimitVerificationAlgorithm(5);
		metric.setVerificationAlgorithm(mlva);
		Context context = new Context();
		context.setValidMetrics(new ArrayList<Metric>());
		context.getValidMetrics().add(metric);
		String source = getSource(sourceFile); 
		
		Parser parser = new CyclomaticComplexityParser(context);
		Measurement mt = parser.parse( null, source);
		return mt;
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
