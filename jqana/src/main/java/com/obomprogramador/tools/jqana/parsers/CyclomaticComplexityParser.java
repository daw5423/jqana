/**
 * jQana - Open Source Java(TM) code quality analyzer.
 * 
 * Copyright 2013 Cleuton Sampaio de Melo Jr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project website: http://www.jqana.com
 */
package com.obomprogramador.tools.jqana.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.antlrparser.JavaLexer;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.defaultimpl.ClassMeasurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMeasurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;

/**
 * Parser used to calculate cyclomatic complexity.
 * @see Parser
 * @author Cleuton Sampaio
 *
 */
public class CyclomaticComplexityParser implements Parser {


	protected Context context;
	protected Logger logger;
	protected Measurement packageMeasurement;
	protected Measurement measurement;
	
	/**
	 * 
	 */
	public CyclomaticComplexityParser(Measurement packageMeasurement,Context context) {
		super();
		logger = LoggerFactory.getLogger(this.getClass());
		this.context = context;
		this.packageMeasurement = packageMeasurement;
	}

	/* (non-Javadoc)
	 * @see com.obomprogramador.tools.anacoja.model.Parser#getParserName()
	 */
	@Override
	public String getParserName() {
		return this.getClass().getName();
	}

	@Override
	public Measurement parse(Class<?> clazz, String sourceCode)  {
		
		this.measurement = new Measurement();
		
		JavaLexer lexer;
		try {
			lexer = new JavaLexer(new ANTLRInputStream(sourceCode));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			JavaParser p = new JavaParser(tokens);
	        ParseTree tree = (ParseTree)(p.compilationUnit()); 
	        ParseTreeWalker walker = new ParseTreeWalker();
	        Metric metric = new DefaultMetric();
	        metric.setMetricName(CYCLOMATIC_COMPLEXITY);
	        int inx = context.getValidMetrics().indexOf(metric);
	        metric = context.getValidMetrics().get(inx);
	        CycloListener cl = new CycloListener(metric, this.measurement ,p);
	        walker.walk(cl, tree); 
	        updatePackageMeasurement();
	        logger.debug("**** Complexidade: " + this.measurement.toString());
		} catch (Exception e) {
			logger.error("Parser: " + e.getMessage());
			context.getErrors().push(e.getMessage());
			
		}
            
		return this.measurement;
	}

	private void updatePackageMeasurement() {
		int indx = this.packageMeasurement.getInnerMeasurements().indexOf(measurement);
		if (indx >= 0) {
			
		}
		
	}
	


}
