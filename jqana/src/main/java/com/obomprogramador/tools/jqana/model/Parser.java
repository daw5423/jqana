package com.obomprogramador.tools.jqana.model;

import java.util.List;

/**
 * A parser analyzes source code and computes metrics values. 
 * @author Cleuton Sampaio
 *
 */
public interface Parser {
	/**
	 * Parser name getter.
	 * @return parser name.
	 */
	public String getParserName();
	/**
	 * Parses the provided source code. 
	 * @param clazz The source class, to get more information.
	 * @param sourceCode source code to analyze.
	 * @return instance of measurement. 
	 */
	public Measurement parse(Class<?> clazz, String sourceCode); 
}
