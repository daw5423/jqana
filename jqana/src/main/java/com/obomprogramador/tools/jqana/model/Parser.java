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
package com.obomprogramador.tools.jqana.model;

/**
 * A parser analyzes source code and computes metrics values.
 * 
 * @author Cleuton Sampaio
 * 
 */
public interface Parser {
    /**
     * Parser name getter.
     * 
     * @return parser name.
     */
    String getParserName();

    /**
     * Parses the provided source code.
     * @param compiledName String the name of the compiled class.
     * @param sourceCode
     *            source code to analyze.
     * @return instance of measurement.
     */
    Measurement parse(String compiledName, String sourceCode);
}
