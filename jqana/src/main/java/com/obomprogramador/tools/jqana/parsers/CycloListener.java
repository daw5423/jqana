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
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.CatchClauseContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ClassDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ConstructorDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.FormalParametersContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ParExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.StatementContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.SwitchLabelContext;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.defaultimpl.GetClassNameFromContext;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

/**
 * This is a JavaBaseListener (ANTLR4) implementation that calculates Cyclomatic
 * Complexity measurement, according to McCabe's calculations
 * (http://www.literateprogramming.com/mccabe.pdf).
 * 
 * The listener counts plus one for: a) Each method; b) Each switch label; c)
 * Each occurence of: "catch", "do", "for", "else if", "if", "throws", "while";
 * d) Each "return" statement that is not the last command of a method;
 * 
 * @author Cleuton Sampaio.
 * 
 */
public class CycloListener extends JavaBaseListener {

    protected String[] predicateNodes = {"do", "for", "throw", "while"};
    protected List<String> listaNodes = Arrays.asList(predicateNodes);
    private Measurement measurement;
    private org.antlr.v4.runtime.Parser parser;
    protected String previousExpression;
    protected Logger logger;
    protected int returnCount;
    protected Deque<Measurement> measurementsStack;
    protected Metric metric;
    protected String mainPackageName;
    protected MetricValue currentMetricValue;
    protected boolean alreadyGotFirstClass;
    protected String lastStatement;
    protected int constructorNumber = 1;

    /**
     * Default constructor.
     * 
     * @param metric
     *            The metric being used for analysis.
     * @param measurement
     *            The expected measurement to return to the Parser.
     * @param parser
     *            Instance of JavaParser (ANTLR4) used to get the AST.
     */
    public CycloListener(Metric metric, Measurement measurement,
            JavaParser parser) {
        this.metric = metric;
        this.measurement = measurement;
        this.parser = parser;
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.measurementsStack = new ArrayDeque<Measurement>();
        this.currentMetricValue = this.getMetricValue(this.measurement);
        if (this.currentMetricValue == null) {
            throw new IllegalArgumentException(
                    "Measurement should have a MetricValue");
        }

    }

    @Override
    public void enterConstructorDeclaration(
            @NotNull ConstructorDeclarationContext ctx) {
        this.previousExpression = null;
        String methodName = "<Constructor " + this.constructorNumber++ + ">";
        this.newMeasurement(methodName, MEASUREMENT_TYPE.METHOD_MEASUREMENT);
        this.currentMetricValue.setValue(1);
        this.previousExpression = null;
        logger.debug("***** (CC) ENTERING CONSTUCTOR: " + methodName);
    }

    @Override
    public void exitConstructorDeclaration(
            @NotNull ConstructorDeclarationContext ctx) {
        logger.debug("***** (CC) EXITING CONSTRUCTOR. CC = "
                + this.currentMetricValue.getValue());
        this.verifyMethodViolation(this.measurement);
        this.consolidateWithOwner();
        this.lastStatement = null;
    }

    @Override
    public void exitClassDeclaration(@NotNull ClassDeclarationContext ctx) {
        this.verifyClassViolation(this.measurement);
        logger.debug("*** (CC) EXITING CLASS. CC = "
                + this.currentMetricValue.getValue());
        this.consolidateWithOwner();
        this.previousExpression = null;

    }

    @Override
    public void enterClassDeclaration(@NotNull ClassDeclarationContext ctx) {
        String className = GetClassNameFromContext.getClassName(ctx);

        if (!alreadyGotFirstClass) {
            // It is the main class name
            this.measurement.setName(className);
            alreadyGotFirstClass = true;
        } else {
            this.newMeasurement(className, MEASUREMENT_TYPE.CLASS_MEASUREMENT);
        }
        this.previousExpression = null;
        logger.debug("*** (CC) ENTERING CLASS: " + className);
    }

    @Override
    public void enterMethodDeclaration(@NotNull MethodDeclarationContext ctx) {
        String methodName = "<no name>";
        for (ParseTree subTree : ctx.children) {
            if (subTree instanceof TerminalNodeImpl) {
                methodName = subTree.toString();
            } else if (subTree instanceof FormalParametersContext) {
                break;
            }
        }
        this.newMeasurement(methodName, MEASUREMENT_TYPE.METHOD_MEASUREMENT);
        this.currentMetricValue.setValue(1);
        this.previousExpression = null;
        logger.debug("***** (CC) ENTERING METHOD: " + methodName);
    }

    @Override
    public void exitMethodDeclaration(@NotNull MethodDeclarationContext ctx) {

        logger.debug("***** (CC) EXITING METHOD. CC = "
                + this.currentMetricValue.getValue());
        this.verifyMethodViolation(this.measurement);
        this.consolidateWithOwner();
        this.lastStatement = null;
    }

    @Override
    public void enterExpression(@NotNull ExpressionContext ctx) {
        if (previousExpression != null) {
            if (!previousExpression.contains(ctx.getText())) {
                checkExpression(ctx);
                previousExpression = ctx.getText();
            }
        } else {
            checkExpression(ctx);
            previousExpression = ctx.getText();
        }
    }

    private void checkExpression(ExpressionContext ctx) {
        if (ctx.getText().charAt(0) != '\"' && ctx.getText().charAt(0) != '\'') {

            int count = countSymbols(ctx.getText(), ":");
            if (count > 0) {
                incMetricValue(count);
                logger.debug("     - Ternaries found: " + count);
            }

            count = countSymbols(ctx.getText(), "||");
            if (count > 0) {
                incMetricValue(count);
                logger.debug("     - OR found: " + count);
            }
            count = countSymbols(ctx.getText(), "&&");
            if (count > 0) {
                incMetricValue(count);
                logger.debug("     - AND found: " + count);
            }
        }
    }

    protected MetricValue newMetricValue() {
        MetricValue mv = new MetricValue();
        mv.setName(this.metric.getMetricName());
        this.currentMetricValue = mv;
        return mv;
    }

    protected MetricValue getMetricValue(Measurement m) {
        MetricValue mv = new MetricValue();
        mv.setName(this.metric.getMetricName());
        int indx = m.getMetricValues().indexOf(mv);
        if (indx >= 0) {
            mv = m.getMetricValues().get(indx);
        } else {
            mv = null;
        }
        return mv;
    }

    protected void consolidateWithOwner() {
        if (!this.measurementsStack.isEmpty()) {
            Measurement owner = this.measurementsStack.peek();
            MetricValue ownerMV = this.getMetricValue(owner);
            this.currentMetricValue = this.getMetricValue(this.measurement);
            ownerMV.setValue(ownerMV.getValue()
                    + this.currentMetricValue.getValue());
            ownerMV.setQtdElements(ownerMV.getQtdElements() + 1);
            this.measurement = this.measurementsStack.pop();
            this.currentMetricValue = getMetricValue(this.measurement);
        }
    }

    protected void newMeasurement(String name, MEASUREMENT_TYPE type) {
        this.measurementsStack.push(this.measurement);
        this.measurement = new Measurement();
        this.measurement.setName(name);
        this.measurement.setType(type);
        Measurement owner = this.measurementsStack.peek();
        owner.getInnerMeasurements().add(this.measurement);
        this.measurement.getMetricValues().add(newMetricValue());
    }

    protected void incMetricValue(int inc) {
        this.currentMetricValue.setValue(this.currentMetricValue.getValue()
                + inc);
    }

    @Override
    public void enterStatement(@NotNull StatementContext ctx) {
        String tipo = ctx.getParent().start.getText();
        if (this.lastStatement != null) {
            if (this.lastStatement.equals("return")) {
                this.incMetricValue(1);
                logger.debug("     - Return not last statement.");
            }
        }
        this.lastStatement = tipo;
    }

    @Override
    public void enterSwitchLabel(@NotNull SwitchLabelContext ctx) {
        if (!ctx.children.get(0).toString().equals("default")) {
            incMetricValue(1);
            logger.debug("     - swith label.");
        } else {
            logger.debug("     - *** default label ignored.");
        }

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
            incMetricValue(1);
            logger.debug("     - Predicate node: " + tipo + " line: "
                    + ctx.getText());
        }
    }

    @Override
    public void enterParExpression(@NotNull ParExpressionContext ctx) {
        if (ctx.getParent().start.getText().equals("if")) {
            incMetricValue(1);
            logger.debug("     - Predicate node IF: " + ctx.getText());
        }
    }

    @Override
    public void enterCatchClause(@NotNull CatchClauseContext ctx) {
        incMetricValue(1);
        logger.debug("     - Catch node: ");
    }

    protected void verifyMethodViolation(Measurement measurement2) {
        this.currentMetricValue.setViolated(this.metric
                .getVerificationAlgorithm().verify(
                        this.currentMetricValue.getValue()));
    }

    protected void verifyClassViolation(Measurement measurement2) {
        for (Measurement m : measurement2.getInnerMeasurements()) {
            MetricValue mv = this.getMetricValue(m);
            if (mv.isViolated()) {
                this.getMetricValue(measurement2).setViolated(true);
                break;
            }
        }
    }

}
