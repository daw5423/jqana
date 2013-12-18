package com.obomprogramador.tools.jqana.model.defaultimpl;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.ProjectProcessor;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;
import com.obomprogramador.tools.jqana.parsers.Lcom4Parser;
import com.obomprogramador.tools.jqana.parsers.RfcParser;

public class DefaultProjectProcessor implements ProjectProcessor {

	protected Context context;
	protected String rootTestResources;
	protected String currentFolderName;
	protected Measurement project;
	protected Logger logger;
	
	public DefaultProjectProcessor(Context context) {
		super();
		this.context = context;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public Measurement process(String projectRootFolderName) throws URISyntaxException, IOException, JAXBException, ParserConfigurationException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.rootTestResources = projectRootFolderName;
		this.project = new Measurement();
		this.project.setName(projectRootFolderName);
		this.project.setType(MEASUREMENT_TYPE.PROJECT_MEASUREMENT);
		
		try {
			processPackages(this.getResourceListing(this.getClass(), rootTestResources));
			DefaultXmlGenerator generator = new DefaultXmlGenerator();
			Document report = generator.serialize(this.project);
			
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (JAXBException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (TransformerException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
			throw(e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			throw(e);
		}
		
		return this.project;
	}
	
	/* (non javadoc)
	 * Process each package and add it's measurement to the project's measurement.
	 * 
	 */
	protected void processPackages(String[] strings) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Measurement packageMeasurement = null;
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
	
	/* (non javadoc)
	 * Process each package as a single folder, analyzing each source file.
	 * 
	 */
	protected void processSingleFolder(Measurement packageMeasurement, String folderName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
	
	/* (non javadoc)
	 * Process the metric set for each source file.
	 * 
	 */
	protected void processMetrics(Measurement packageMeasurement, String sourceFile) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		logger.debug("Source file: " + sourceFile);
		String sourceCode = this.getSource(sourceFile);
		processCyclomaticMetric(sourceCode, packageMeasurement);
		processLcom4Metric(sourceCode, packageMeasurement);
		processRfcMetric(sourceCode, packageMeasurement);
	}
	
	/* (non javadoc)
	 * Analyzes Cyclomatic complexity for a source file.
	 */
	protected void processCyclomaticMetric(String sourceFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Context context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new CyclomaticComplexityParser(packageMeasurement, context);
		Measurement mt = parser.parse( null, sourceFile);
		
	}
	
	/* (non javadoc)
	 * Analyzes LCOM4 for a source file.
	 */
	protected void processLcom4Metric(String sourceFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Context context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new Lcom4Parser(packageMeasurement, context);
		Measurement mt = parser.parse( null, sourceFile);
		
	}
	
	/* (non javadoc)
	 * Analyzes RFC for a source file.
	 * 
	 */
	private void processRfcMetric(String sourceFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Context context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new RfcParser(packageMeasurement, context);
		Measurement mt = parser.parse( null, sourceFile);
		
	}
	
	protected String getSource(String sourceFile) {
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

	protected InputStream getStream(String sourceFile) {
		return this.getClass().getClassLoader().getResourceAsStream(sourceFile);
	}
	
	  /* (non javadoc)
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
	  protected String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
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
