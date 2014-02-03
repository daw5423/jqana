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

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.AbstractMetricParser;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

/**
 * Parser used to calculate cyclomatic complexity.
 * 
 * @see Parser
 * @author Cleuton Sampaio
 * 
 */
public class CyclomaticComplexityParser extends AbstractMetricParser {


    /**
     * Constructor with fields.
     * @param packageMeasurement Measurement the package's measurement.
     * @param context Context the context to use.
     */
    public CyclomaticComplexityParser(Measurement packageMeasurement,
            Context context) {
        super(packageMeasurement, context, "metric.cc.name");
    }

    /**
     * Listener getter.
     * @return JavaBaseListener listener.
     */
    @Override
    public JavaBaseListener getListener(JavaParser p) {
        JavaBaseListener jbl = new CycloListener(this.metric, this.measurement,
                p);
        return jbl;
    }

    /**
     * After processing override.
     * 
     */
    @Override
    public void afterProcessing() {
        // Compute the average:
        MetricValue mv = this.measurement.getMetricValue(context.getBundle()
                .getString(this.metricResourceId));
        if (mv.getQtdElements() == 0) {
            mv.setQtdElements(1);
        }
        mv.setValue(mv.getValue() / mv.getQtdElements());
    }

}
