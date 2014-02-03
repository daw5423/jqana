package com.obomprogramador.tools.jacana;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXml2HtmlConverter;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;

public class TestXml2HtmlConverter {

    /*
     * (non javadoc) This tests only the XSL conversion, not invoking the
     * metrics processor. It uses a plain XML file.
     * 
     * The real test is inside IT, which invokes the processor and plugin
     * classes.
     */
    @Test
    public void test() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String sourceXml = this.getSource("testreport.xml");
        DefaultXml2HtmlConverter converter = new DefaultXml2HtmlConverter();
        try {
            String output = converter.convert(sourceXml);

            assertTrue(output != null && verifyResult("testreport.xml", output));

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (TransformerException e) {
            fail(e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            fail(e.getMessage());
        } catch (ParserConfigurationException e) {
            fail(e.getMessage());
        } catch (SAXException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (JAXBException e) {
            fail(e.getMessage());
        }

    }

    private boolean verifyResult(String sourceXml, String output)
            throws ParserConfigurationException, SAXException, IOException,
            JAXBException {
        boolean result = true;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document report = db.parse(this.getClass().getClassLoader()
                .getResourceAsStream(sourceXml));
        JAXBContext jcontext = JAXBContext.newInstance(Measurement.class);
        Unmarshaller m = jcontext.createUnmarshaller();
        Measurement projectMeasurement = (Measurement) m.unmarshal(report);
        List<Measurement> packages = projectMeasurement.getInnerMeasurements();

        for (Measurement packageM : packages) {
            if (!output.contains("<a name=\"" + packageM.getName() + "\">")) {
                System.out.println("*** Error a package is missing: "
                        + packageM.getName());
                result = false;
                break;
            }
            for (Measurement classM : packageM.getInnerMeasurements()) {
                if (!output.contains("<a href=\"#" + classM.getName() + "\">")) {
                    System.out.println("*** Error a class is missing: "
                            + classM.getName());
                    result = false;
                    break;
                }
                StringBuilder tagClass = new StringBuilder();
                tagClass.append("<a href=\"#" + classM.getName() + "\">"
                        + classM.getName() + "</a></td>");
                for (MetricValue mv : classM.getMetricValues()) {
                    String appendString = "<td style=\"text-align:center;\">"
                            + mv.getValue() + "</td>";
                    String searchString = tagClass.toString() + appendString;
                    if (!output.contains(searchString)) {
                        appendString = "<td style=\"text-align:center;\"><span style=\"font-weight:bold;color:red\">"
                                + mv.getValue() + "</span></td>";
                        searchString = tagClass.toString() + appendString;
                        if (!output.contains(searchString)) {
                            System.out.println("*** Metric missing for class: "
                                    + classM.getName() + " Metric: "
                                    + mv.getName());
                            result = false;
                            break;
                        }
                    }
                    tagClass.append(appendString);
                }
            }
        }

        return result;
    }

    private String getSource(String string) {
        String sourceUri = this.getClass().getClassLoader().getResource(string)
                .getFile();
        String sourceCode = null;
        sourceCode = readFile(sourceUri);
        return sourceCode;
    }

    private String readFile(String fileName) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
