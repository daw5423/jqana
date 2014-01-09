package com.obomprogramador.tools.jqana.model.defaultimpl;

import com.obomprogramador.tools.jqana.context.Context;

/**
 * Get the test results, returning a String array;
 * @author Cleuton Sampaio
 *
 */
public class RetriveTestResults {
	/**
	 * Get "test.results" key from property file and return a String Array.
	 * Format:
	 * {
	 * 	<class>, <class>, ...
	 * }
	 * 
	 * Each class instance is an array:
	 *  <package>, <class>, <cc value>, <lcom4 value>, <rfc value>
	 * @param context Context the context to get the property file from.
	 * @return String[][] String array.
	 */
	public static String[][] getResults(Context context) {
		String [] classes = context.getBundle().getString("test.results").split("@");
		String [][] results = new String[classes.length][5];
		for (int x=0; x<classes.length; x++) {
			String [] classMetrics = classes[x].split(":");
			for (int y=0; y<classMetrics.length; y++) {
				results[x][y] = new String(classMetrics[y]);
			}
		}
		return results;
	}
}
