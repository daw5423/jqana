package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.obomprogramador.tools.jqana.model.Measurement;

@XmlRootElement(name="class")
@XmlSeeAlso({DefaultMeasurement.class, MethodMeasurement.class})
public class ClassMeasurement extends DefaultMeasurement {

	public ClassMeasurement() {
		super();
		this.innerMeasurements = new ArrayList<Measurement>();
	}

}
