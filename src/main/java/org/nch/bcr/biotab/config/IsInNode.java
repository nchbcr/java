package org.nch.bcr.biotab.config;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'in' closure. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "isIn")
@XmlAccessorType(XmlAccessType.FIELD)
public class IsInNode extends Criteria {
	
	public IsInNode() {super();}
	
	public IsInNode(String diseaseList) {
		super(diseaseList);
	}

	public boolean test(String diseaseCode) {
		// This is a test to test whether the containing element is to be excluded from the specified study. 
		// So, if the specified study IS IN the list of studies that exist in the config file for this 
		// element, then it should be excluded.
		return getDiseases().get(diseaseCode)!=null;
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(IsInNode.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(this, writer);

			return writer.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "(toXmlString ERROR: " + e.getMessage() + ")";
		}
	}
}
