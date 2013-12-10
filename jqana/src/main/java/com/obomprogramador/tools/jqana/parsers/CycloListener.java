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

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.BlockContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ClassBodyContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ClassDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ConstructorDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.FormalParametersContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.InnerCreatorContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodBodyContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.PackageDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.StatementContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.SwitchLabelContext;
import com.obomprogramador.tools.jqana.context.GlobalConstants.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMeasurement;

/**
 * This is a JavaBaseListener (ANTLR4) implementation that calculates Cyclomatic Complexity
 * measurement, according to McCabe's calculations (http://www.literateprogramming.com/mccabe.pdf).
 * 
 * The listener counts plus one for:
 * a) Each method;
 * b) Each switch label;
 * c) Each occurence of: "catch", "do", "for", "else if", "if", "throws", "while";
 * d) Each "return" statement that is not the last command of a method;
 * 
 * @author Cleuton Sampaio.
 *
 */
public class CycloListener extends JavaBaseListener {
	
	
	protected String [] predicateNodes = {
			   "catch", "do", "for", "else if", "if", "throws", "while"
		};
	protected List<String> listaNodes = Arrays.asList(predicateNodes);
	private Measurement measurement;
	private org.antlr.v4.runtime.Parser parser;
	protected String previousExpression;
	protected Logger logger;
	protected int returnCount;
	protected Deque<Measurement> measurementsStack;
	protected Metric metric;

	
	/**
	 * Default constructor. 
	 * @param metric The metric being used for analysis.
	 * @param measurement The expected measurement to return to the Parser.
	 * @param parser Instance of JavaParser (ANTLR4) used to get the AST.
	 */
	public CycloListener(Metric metric, Measurement measurement, JavaParser parser) {
		this.metric = metric;
		this.measurement = measurement;
		this.parser = parser;
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.measurementsStack = new ArrayDeque<Measurement>();
	}



	@Override
	public void enterPackageDeclaration(@NotNull PackageDeclarationContext ctx) {
		String packageName = ctx.getText().substring(7);
		measurement.setPackageName(packageName);
		logger.debug(packageName);
	}

	
	
	
	@Override
	public void enterConstructorDeclaration(
			@NotNull ConstructorDeclarationContext ctx) {
		this.previousExpression = null;
	}



	@Override
	public void exitClassDeclaration(@NotNull ClassDeclarationContext ctx) {
		this.measurement = this.measurementsStack.pop();
		this.verifyClassViolation(this.measurement);
		if (!this.measurementsStack.isEmpty()) {
			Measurement owner = this.measurementsStack.peek();
			owner.setMetricValue(owner.getMetricValue() + this.measurement.getMetricValue());
			this.measurement = owner;
		}
		this.previousExpression = null;
	}


	@Override
	public void enterClassDeclaration(@NotNull ClassDeclarationContext ctx) {
		int posCurly = ctx.getText().indexOf('{');
		String className = ctx.getText().substring(5,posCurly);
		
		if (!this.measurementsStack.isEmpty()) {
			this.measurement = new DefaultMeasurement();
			Measurement owner = this.measurementsStack.peek();
			owner.getInnerMeasurements().add(measurement);
		}
		this.measurement.setMetric(this.metric);
		this.measurement.setMeasurementType(MEASUREMENT_TYPE.CLASS_MEASUREMENT.ordinal());
		this.measurement.setClassName(className);
		this.measurementsStack.push(measurement);
		
	}


	@Override
	public void enterMethodDeclaration(@NotNull MethodDeclarationContext ctx) {
		String methodName = "<no name>";
		for (ParseTree subTree : ctx.children) {
			if (subTree instanceof TerminalNodeImpl) {
				methodName = subTree.toString();
			}
			else if (subTree instanceof FormalParametersContext) {
				break;
			}
		}
		Measurement classMeasurement = this.measurement;
		this.measurement = new DefaultMeasurement();
		this.measurement.setClassName(classMeasurement.getClassName());
		this.measurement.setPackageName(classMeasurement.getPackageName());
		this.measurement.setMetric(classMeasurement.getMetric());
		this.measurement.setMethodName(methodName);
		this.measurement.setMeasurementType(MEASUREMENT_TYPE.METHOD_MEASUREMENT.ordinal());
		this.measurement.setMetricValue(1);
		classMeasurement.getInnerMeasurements().add(this.measurement);
		this.measurementsStack.push(this.measurement);
		logger.debug("Entering method: " + methodName);
		this.previousExpression = null;
	}


	@Override
	public void exitMethodBody(@NotNull MethodBodyContext ctx) {
		if (this.returnCount > 1) {
			this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
			logger.debug("PN: has more \"return\" statements ");
		}
		
		this.measurement = this.measurementsStack.pop();
		this.verifyMethodViolation(this.measurement);
		if (!this.measurementsStack.isEmpty()) {
			Measurement owner = this.measurementsStack.peek();
			owner.setMetricValue(owner.getMetricValue() + this.measurement.getMetricValue());
			this.measurement = owner;
		}
	}



	@Override
	public void enterExpression(@NotNull ExpressionContext ctx) {
		if (previousExpression != null) {
			if (!previousExpression.contains(ctx.getText())) {
				checkExpression(ctx);
				previousExpression = ctx.getText();
			}
		}
		else {
			checkExpression(ctx);
			previousExpression = ctx.getText();
		}
	}



	private void checkExpression(ExpressionContext ctx) {
		if (ctx.getText().charAt(0) != '\"' && ctx.getText().charAt(0) != '\'') {
			int count = countSymbols(ctx.getText(), ":");
			this.measurement.setMetricValue(this.measurement.getMetricValue() + count);
			if (count > 0) {
				logger.debug("Ternaries found: " + ctx.getText());	
			}
			count = countSymbols(ctx.getText(), "||");
			this.measurement.setMetricValue(this.measurement.getMetricValue() + count);
			if (count > 0) {
				logger.debug("OR found: " + ctx.getText());	
			}
			count = countSymbols(ctx.getText(), "&&");
			this.measurement.setMetricValue(this.measurement.getMetricValue() + count);
			if (count > 0) {
				logger.debug("AND found: " + ctx.getText());	
			}
		}
	}

	



	@Override
	public void enterStatement(@NotNull StatementContext ctx) {
		String tipo = ctx.getParent().start.getText();
		if (tipo.equals("return")) {
			returnCount++;
			logger.debug("Maybe: " + tipo +  " >>>> "+  ctx.getText());
		}
	}



	@Override
	public void enterSwitchLabel(@NotNull SwitchLabelContext ctx) {
		this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
		logger.debug("PN: "+  ctx.getText());
	}

	private int countSymbols(String originalText, String whatToFind) {
		return StringUtils.countMatches(originalText, whatToFind);
	}

	@Override
	public void enterBlock(@NotNull BlockContext ctx) {
		String tipo = ctx.getParent().start.getText();
		
		if (tipo.equals("{")) {
			tipo = ctx.getParent().getParent().start.getText();
		}
		
		if (listaNodes.contains(tipo)) {
			this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
			logger.debug("PN: " + tipo +  " >>>> "+  ctx.getText());
		}
	}

	protected void initMeasurement(Measurement measurement2, MEASUREMENT_TYPE type) {
		measurement2.setPackageName("<default>");
		measurement2.setClassName(null);
		measurement2.setMetric(this.metric);
		measurement2.setMeasurementType(type.ordinal());
	}
	
	protected void verifyMethodViolation(Measurement measurement2) {
		measurement2.setViolated(measurement2.getMetric().getVerificationAlgorithm().verify(measurement2.getMetricValue()));
	}
	
	protected void verifyClassViolation(Measurement measurement2) {
		for (Measurement m : measurement2.getInnerMeasurements()) {
			if (m.isViolated()) {
				measurement2.setViolated(true);
				break;
			}
		}
	}

}
