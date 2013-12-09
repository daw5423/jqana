package com.obomprogramador.tools.jqana.parsers;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.CompilationUnitContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ConstructorDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodDeclarationContext;
import com.obomprogramador.tools.jqana.model.Measurement;

public class RfcListener extends JavaBaseListener {
	
	protected Logger logger;
	protected JavaParser parser;
	protected Measurement measurement;
	protected boolean aConstructorWasFound;
	private   String previousExpression;

	public RfcListener(Measurement measurement, JavaParser p) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.measurement = measurement;
		this.parser = p;
	}

	/**
	 * Counts all constructors of the class. 
	 */
	@Override
	public void enterConstructorDeclaration(
			@NotNull ConstructorDeclarationContext ctx) {
		aConstructorWasFound = true;
		this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
		this.previousExpression = null;
	}
	
	
	/**
	 * Before exiting the Listener, we need to know whether we have found a constructor
	 * or not. 
	 */
	@Override
	public void exitCompilationUnit(@NotNull CompilationUnitContext ctx) {
		if (!this.aConstructorWasFound) {
			this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
		}
	}

	/**
	 * Counts all class' methods.
	 */
	@Override
	public void enterMethodDeclaration(@NotNull MethodDeclarationContext ctx) {
		this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
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
					logger.debug("Found Method or constructor call! ");
					this.measurement.setMetricValue(this.measurement.getMetricValue() + 1);
				}
			}
			start = pos + 1;
			if (start >= expression.length()) {
				break;
			}
		}
	}


}
