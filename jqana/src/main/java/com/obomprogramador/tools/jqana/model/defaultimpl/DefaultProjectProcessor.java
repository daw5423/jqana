package com.obomprogramador.tools.jqana.model.defaultimpl;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.ProjectProcessor;
import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;
import com.obomprogramador.tools.jqana.parsers.Lcom4Parser;
import com.obomprogramador.tools.jqana.parsers.RfcBcelParser;
import com.obomprogramador.tools.jqana.parsers.RfcParser;

public class DefaultProjectProcessor implements ProjectProcessor {

	protected Context context;
	protected File projectSourceRoot;
	protected File projectObjectRoot;
	protected String currentFolderName;
	protected Measurement project;
	protected Logger logger;
	protected Log log;
	protected enum MSG_TYPE {INFO, DEBUG, ERROR};
	
	
	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public DefaultProjectProcessor(Context context) {
		super();
		this.context = context;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public Measurement process(String projectName, File projectSourceRoot, File projectObjectRoot) throws URISyntaxException, IOException, JAXBException, ParserConfigurationException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.projectSourceRoot = projectSourceRoot;
		this.projectObjectRoot = projectObjectRoot;
		this.project = new Measurement();
		this.project.setName(projectName);
		this.project.setType(MEASUREMENT_TYPE.PROJECT_MEASUREMENT);
		
		logMsg("**** Project: " + projectName + ", resources: " + projectSourceRoot.getPath(), MSG_TYPE.DEBUG);
		try {
			processFolder(this.projectSourceRoot);
			
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
		
		updatePackagesAggregates();

		return this.project;
	}
	

	protected void updatePackagesAggregates() {
		for (Measurement m : this.project.getInnerMeasurements()) {
			MetricValue mv = m.getMetricValue(this.context.getBundle().getString("metric.cc.name"));
			mv.setValue((double)mv.getValue() / (double)mv.getQtdElements());
			mv = m.getMetricValue(this.context.getBundle().getString("metric.rfc.name"));
			mv.setValue((double)mv.getValue() / (double)mv.getQtdElements());
		}
		
	}

	/* (non javadoc)
	 * Process each package and add it's measurement to the project's measurement.
	 * 
	 */
	protected void processFolder(File sourceDir) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		boolean hasJavaFiles = false;
		File [] listFiles = sourceDir.listFiles();
		Measurement packageMeasurement = new Measurement();
		packageMeasurement.setName(getPackageName(sourceDir));
		packageMeasurement.setType(MEASUREMENT_TYPE.PACKAGE_MEASUREMENT);
		logMsg("**** Pagkage: " + sourceDir.getName(), MSG_TYPE.DEBUG);
		for (int x=0; x<listFiles.length; x++) {
			File oneFile = listFiles[x];
			if (oneFile.isFile()) {
				if (isJavaFile(oneFile)) {
					hasJavaFiles = true;
				}
				processMetrics(packageMeasurement,oneFile);
			}
			else {
				// Is a directory (sub package)
				processFolder(oneFile);
			}
		}
		if (hasJavaFiles) {
			// if this package has java files, then we add it to the project's measurements
			project.getInnerMeasurements().add(packageMeasurement);
			
		}
		
	}
	
	protected String getPackageName(File sourceDir) {
		String packageName = null;
		if (sourceDir.getName().equalsIgnoreCase("java")) {
			packageName = "<default>";
		}
		else {
			int inx = sourceDir.getPath().indexOf("java\\");
			packageName = sourceDir.getPath().substring(inx + 5);
			packageName = packageName.replace("\\", ".");
		}
		return packageName;
	}

	protected boolean isJavaFile(File oneFile) {
		boolean returnCode = false;
		if (FileUtils.getExtension(oneFile.getName()).equalsIgnoreCase("java")) {
			returnCode = true;
		}
		return returnCode;
	}

	/* (non javadoc)
	 * Process the metric set for each source file.
	 * 
	 */
	protected void processMetrics(Measurement packageMeasurement, File oneFile) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		logger.debug("Source file: " + oneFile.getName());
		String sourceCode = this.getSource(oneFile);
		processCyclomaticMetric(sourceCode, packageMeasurement);
		processLcom4Metric(sourceCode, packageMeasurement);
		processRfcMetric(oneFile, packageMeasurement);
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
		logger.debug(mt.toString());
	}
	
	/* (non javadoc)
	 * Analyzes RFC for a source file.
	 * 
	 */
	protected void processRfcMetric(File oneFile, Measurement packageMeasurement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Context context = new Context();
		String objectPath = getObjectFilePath(oneFile);
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		Parser parser = new RfcBcelParser(packageMeasurement, context);
		Measurement mt = parser.parse(objectPath, null);
		
	}
	
	protected String getObjectFilePath(File oneFile) {
		String objectPath = oneFile.getPath().replace(".java", ".class");
		objectPath = objectPath.replace(this.projectSourceRoot.getPath(), this.projectObjectRoot.getPath());
		return objectPath;
	}

	protected String getSource(File oneFile) {
		BufferedReader br = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	    	br = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile),"UTF-8"));
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
	


	  protected void logMsg(String msg, MSG_TYPE type) {
		  if (this.log == null) {
			  // using internal logger
			  switch (type) {
			  case INFO:
				  logger.info(msg);
				  break;
			  case DEBUG:
				  logger.debug(msg);
				  break;
			  case ERROR:
				  logger.error(msg);
			  }
		  }
		  else {
			  // using maven plugin logger
			  switch (type) {
			  case INFO:
				  this.log.info(msg);
				  break;
			  case DEBUG:
				  this.log.debug(msg);
				  break;
			  case ERROR:
				  this.log.error(msg);
			  }
		  }
	  }
	  
}
