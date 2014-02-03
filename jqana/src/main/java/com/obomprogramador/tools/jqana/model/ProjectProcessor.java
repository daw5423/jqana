package com.obomprogramador.tools.jqana.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Processes all the project's packages.
 * 
 * @author Cleuton Sampaio
 * 
 */
public interface ProjectProcessor {
    /**
     * This method parses the specified folder, processing each found package,
     * and returning a project consolidated measurement.
     * 
     * @param projectName String the project's name. 
     * @param projectSourceRoot
     *            File. Source root directory.
     * @param projectObjectRoot
     *            File. Compiled classes directory.
     * @return Project consolidated measurement.
     * @throws URISyntaxException in case of a resource location error.
     * @throws IOException in case of any IO error.
     * @throws JAXBException in case of a Marshalling / Unmarshalling error.
     * @throws ParserConfigurationException Any DOM Parser configuration problem.
     * @throws TransformerException In case of any problem transforming Object to XML.
     * @throws ClassNotFoundException In case of any class load error.
     * @throws InstantiationException In case of problems instantiating classes.
     * @throws IllegalAccessException In case of problem trying to marshall the instances to XML.
     */
    Measurement process(String projectName, File projectSourceRoot,
            File projectObjectRoot) throws URISyntaxException, IOException,
            JAXBException, ParserConfigurationException, TransformerException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException;
}
