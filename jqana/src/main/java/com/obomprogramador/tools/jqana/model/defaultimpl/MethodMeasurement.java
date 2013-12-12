package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.obomprogramador.tools.jqana.model.Measurement;

@XmlRootElement(name="method")
@XmlSeeAlso({DefaultMeasurement.class})
public class MethodMeasurement extends DefaultMeasurement {

	
	public MethodMeasurement() {
		super();
		this.innerMeasurements = new ArrayList<Measurement>();
	}

}
