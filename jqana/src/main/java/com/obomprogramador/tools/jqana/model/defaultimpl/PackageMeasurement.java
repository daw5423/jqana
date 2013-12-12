package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.obomprogramador.tools.jqana.model.Measurement;

@XmlRootElement(name="package")
@XmlSeeAlso({DefaultMeasurement.class, ClassMeasurement.class})
public class PackageMeasurement extends DefaultMeasurement {

	public PackageMeasurement() {
		super();
		this.innerMeasurements = new ArrayList<Measurement>();
	}

}
