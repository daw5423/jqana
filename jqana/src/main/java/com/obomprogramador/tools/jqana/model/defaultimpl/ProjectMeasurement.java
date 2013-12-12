package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.obomprogramador.tools.jqana.model.Measurement;

@XmlRootElement(name="project")
@XmlSeeAlso({DefaultMeasurement.class, PackageMeasurement.class})
public class ProjectMeasurement extends DefaultMeasurement {

	protected String projectName;
	
	public ProjectMeasurement() {
		super();
		this.innerMeasurements = new ArrayList<Measurement>();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
}
