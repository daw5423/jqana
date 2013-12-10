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
package com.obomprogramador.tools.jqana.mavenplugin;

import java.io.File;
import java.util.Locale;

import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

/**
 * jQana - Open Source java source code quality analyzer.
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
 * This is the Mojo that implements jQana maven plugin.
 * 
 * @author Cleuton Sampaio
 *
 */
public class JqanaMojo extends AbstractMavenReport {
	
    /**
     * Report output directory. Note that this parameter is only relevant if the goal is run from the command line or
     * from the default build lifecycle. If the goal is run indirectly as part of a site generation, the output
     * directory configured in the Maven Site Plugin is used instead.
     */
    @Parameter( defaultValue = "${project.reporting.outputDirectory}" )
    private File outputDirectory;
 
    /**
     * The Maven Project.
     */
    @Component
    private MavenProject project;
 
    /**
     * Doxia Site Renderer.
     */
    @Component
    protected Renderer siteRenderer;

	public JqanaMojo() {
		super();
	}

	@Override
	public String getDescription(Locale arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Locale arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void executeReport(Locale arg0) throws MavenReportException {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getOutputDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MavenProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Renderer getSiteRenderer() {
		// TODO Auto-generated method stub
		return null;
	}

}
