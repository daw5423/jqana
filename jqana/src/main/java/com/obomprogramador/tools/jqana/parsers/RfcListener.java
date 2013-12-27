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

import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ClassDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.CompilationUnitContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ConstructorDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodDeclarationContext;
import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

/**
 * Implementation of JavaBaseListener (ANTLR4) that calculates a Response For Class metric.
 * 
 * How does it work?
 * It counts plus one for:
 * a) Each constructor of the class. If no constructor is found, then it counts one for the default "no-args" constructor;
 * b) Each class' method;
 * c) Each object instantiation (a constructor is invoked);
 * d) Each method call (member or not).
 * 
 * Then the received Measurement instance is updated, and the Parser gets the value.
 * 
 * @author Cleuton Sampaio
 *
 */
public class RfcListener extends JavaBaseListener {
	
	protected Logger logger;
	protected JavaParser parser;
	protected Measurement measurement;
	protected MetricValue metricValue;
	protected boolean aConstructorWasFound;
	protected   String previousExpression;
	protected String mainPackageName;
	protected String mainClassName;
	protected Context context;
	protected boolean alreadyFindMainClass;

	/**
	 * Default constructor.
	 * @param context 
	 * @param measurement the Measurement instance to use. Its "metricValue" property will be updated.
	 * @param p The JavaParser used to parse the Tree.
	 */
	public RfcListener(Context context, Measurement measurement, JavaParser p) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.measurement = measurement;
		this.parser = p;
		this.context = context;
		this.metricValue = this.measurement.getMetricValue(this.context.getBundle().getString("metric.rfc.name"));
	}

	
	
	@Override
	public void enterClassDeclaration(@NotNull ClassDeclarationContext ctx) {
		if (!this.alreadyFindMainClass) {
			int posCurly = ctx.getText().indexOf('{');
			mainClassName = ctx.getText().substring(5,posCurly);
			this.measurement.setName(mainClassName);
			this.alreadyFindMainClass = true;
		}
		this.previousExpression = null;
	}



	/**
	 * Counts all constructors of the class. 
	 */
	@Override
	public void enterConstructorDeclaration(
			@NotNull ConstructorDeclarationContext ctx) {
		aConstructorWasFound = true;
		this.metricValue.setValue(this.metricValue.getValue() + 1);
		this.previousExpression = null;
	}
	
	
	/**
	 * Before exiting the Listener, we need to know whether we have found a constructor
	 * or not. 
	 */
	@Override
	public void exitCompilationUnit(@NotNull CompilationUnitContext ctx) {
		if (!this.aConstructorWasFound) {
			this.metricValue.setValue(this.metricValue.getValue() + 1);
		}
		this.previousExpression = null;
	}

	/**
	 * Counts all class' methods.
	 */
	@Override
	public void enterMethodDeclaration(@NotNull MethodDeclarationContext ctx) {
		this.metricValue.setValue(this.metricValue.getValue() + 1);
		this.previousExpression = null;
	}
	
	/**
	 * Counts each method call. A method call is an expression member followed by a "(".
	 */
	@Override
	public void enterExpression(@NotNull ExpressionContext ctx) {
		if (previousExpression != null) {
			if (!previousExpression.contains(ctx.getText())) {
				analizeExpression(ctx);
				logger.debug(ctx.getText());
				previousExpression = ctx.getText();
			}
		}
		else {
			analizeExpression(ctx);
			logger.debug(ctx.getText());
			previousExpression = ctx.getText();
		}
	}

	/** 
	 * (non javadoc
	 * Analyze expression text. If a "(" is found, and it is not preceeded by an
	 * operator, then it is considered a method or a constructor call.
	 */
	private void analizeExpression(ExpressionContext ctx) {
		String expression = ctx.getText();
		int start = 0;
		int pos = 0;
		while ((pos = StringUtils.indexOf(expression, '(', start)) >= 0) {
			if (pos > start) {
				char previous = expression.charAt(pos - 1);
				if (Character.isJavaIdentifierPart(previous)) {
					logger.debug("Found Method or constructor call! " + ctx.getText());
					this.metricValue.setValue(this.metricValue.getValue() + 1);
				}
			}
			start = pos + 1;
			if (start >= expression.length()) {
				break;
			}
		}
	}


}
