package def;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;




public class BadRfc extends JFrame implements ActionListener {
	JButton btn = new JButton("OK");

	public BadRfc() throws HeadlessException {
		super();
		this.getContentPane().add(btn);
		btn.addActionListener(this);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {

			e1.printStackTrace();
		}
		Document report = db.newDocument();
	    JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(this.getClass());
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}

	    Marshaller m = null;
		try {
			m = context.createMarshaller();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	    try {
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (PropertyException e1) {
			e1.printStackTrace();
		}

	    try {
			m.marshal(this, report);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}	
	    
	    try {
		    String output = null;
	        TransformerFactory factory = TransformerFactory.newInstance();
	        StreamSource xslStream = new StreamSource(this.getClass().getClassLoader().getResourceAsStream("xml2html.xsl"));
	        Transformer transformer = factory.newTransformer(xslStream);
	        StreamSource in = new StreamSource(new ByteArrayInputStream("TESTE".getBytes("UTF-8")));
	        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	        StreamResult out = new StreamResult(bOut);
	        transformer.setOutputProperty("omit-xml-declaration", "yes");
	        transformer.transform(in, out);
	        output = new String(bOut.toByteArray(), "UTF-8");
	        
	        JAXBContext jcontext = JAXBContext.newInstance(this.getClass());
			Unmarshaller m1 = jcontext.createUnmarshaller();
			Date dt = new Date();
			System.out.println(dt);
			System.out.println("TESTE");
			JOptionPane.showMessageDialog(null, "OK");
			this.toString();
			this.dispose();
			this.getComponentCount();
			this.isActive();
			this.notify();
			btn.doClick();
			"Teste".replace('.', ';');
			"Teste".replaceAll("t", "t");
			"Teste".isEmpty();
			"Teste".charAt(0);
			"Teste".length();
			"Teste".getBytes();
			"Teste".wait();
			"Teste".getClass();
			this.getBackground();
			this.getAlignmentX();
			this.getAlignmentY();
	    }
	    catch (Exception ex) {
	    	
	    }
	    
       		
		
	}
	
	

	
	
}
