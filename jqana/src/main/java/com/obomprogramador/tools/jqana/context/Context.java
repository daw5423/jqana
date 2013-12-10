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
package com.obomprogramador.tools.jqana.context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.obomprogramador.tools.jqana.model.Metric;
/**
 * jQana - Open Source java source code quality analyzer.
 * 
 * This is the project's context class.
 * 
 * @author Cleuton Sampaio
 *
 */
public class Context {

	private String currentClassUri;
	private Deque<String> errors;
	private List<Metric> validMetrics;
	
	public Context() {
		super();
		this.errors = new ArrayDeque<String>();
	}
	
	public List<Metric> getValidMetrics() {
		return validMetrics;
	}

	public void setValidMetrics(List<Metric> validMetrics) {
		this.validMetrics = validMetrics;
	}

	public Deque<String> getErrors() {
		return errors;
	}

	public void setErrors(Deque<String> errors) {
		this.errors = errors;
	}

	
	public String getCurrentClassUri() {
		return currentClassUri;
	}

	public void setCurrentClassUri(String currentClassUri) {
		this.currentClassUri = currentClassUri;
	}

	
}
