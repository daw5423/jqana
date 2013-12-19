package com.obomprogramador.tools.jacana;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultProjectProcessor;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXmlGenerator;

public class TestUsingXmlGenerator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String rootTestResources = "unit-test-sources";
	private Measurement project;
	
	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			DefaultProjectProcessor dpp = new DefaultProjectProcessor(new Context());
			File sourceDir = new File(this.getClass().getClassLoader().getResource(rootTestResources).toURI());
			this.project = dpp.process("Teste", sourceDir);
			DefaultXmlGenerator generator = new DefaultXmlGenerator();
			Document report = generator.serialize(this.project);
			assertTrue(report != null);
			logger.debug(generator.xml2String(report));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
	}
	

}