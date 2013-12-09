/**
 * 
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
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMeasurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;

/**
 * Calcula os nós predicados, somando 1: V(G) = P + 1.
 * Nós predicados: 
 * "if", "for", "do", "while", "catch", "throw", "case", "default", "else if"
 * 
 * Expressões: Opções de operador ternário (":"), AND ("&&") e OR ("||").
 * 
 * Cada caminho é contado como um Nó predicado.
 * 
 *
 * 
 * @author Cleuton Sampaio
 *
 */
public class CyclomaticComplexityParser implements Parser {


	protected Context context;
	protected Measurement measurement;
	protected Logger logger;
	
	/**
	 * 
	 */
	public CyclomaticComplexityParser(Context context) {
		super();
		logger = LoggerFactory.getLogger(this.getClass());
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.obomprogramador.tools.anacoja.model.Parser#getParserName()
	 */
	@Override
	public String getParserName() {
		return this.getClass().getName();
	}

	/* (non-Javadoc)
	 * @see com.obomprogramador.tools.anacoja.model.Parser#parse(java.lang.Class, java.lang.String)
	 */
	@Override
	public Measurement parse(Class<?> clazz, String sourceCode)  {
		
		this.measurement = new DefaultMeasurement();
		
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
	        logger.debug("**** Complexidade: " + this.measurement.toString());
		} catch (Exception e) {
			context.getErrors().push(e.getMessage());
			logger.error(e.getMessage());
		}
            
		return this.measurement;
	}
	


}
