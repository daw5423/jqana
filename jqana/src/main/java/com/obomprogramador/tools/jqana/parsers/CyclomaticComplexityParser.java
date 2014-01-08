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

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaLexer;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.context.GlobalConstants;
import com.obomprogramador.tools.jqana.model.AbstractMetricParser;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;

import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;

/**
 * Parser used to calculate cyclomatic complexity.
 * @see Parser
 * @author Cleuton Sampaio
 *
 */
public class CyclomaticComplexityParser extends AbstractMetricParser {


	
	/**
	 * 
	 */
	public CyclomaticComplexityParser(Measurement packageMeasurement,Context context) {
		super(packageMeasurement,context,"metric.cc.name");
	}


	@Override
	public JavaBaseListener getListener(JavaParser p) {
		JavaBaseListener jbl = new CycloListener(this.metric, this.measurement, p);
		return jbl;
	}


	@Override
	public void afterProcessing() {
		// Compute the average:
        MetricValue mv = this.measurement.getMetricValue(context.getBundle().getString(this.metricResourceId));
        if (mv.getQtdElements() == 0) {
        	mv.setQtdElements(1);
        }
        mv.setValue(mv.getValue() / mv.getQtdElements());
	}

}
