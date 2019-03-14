package org.nch.bcr.biotab.versions;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains the version of the XSDs that is currently the production release. The TCGA program can have 
 * only ONE XSD version in production at a time. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "production")
@XmlAccessorType(XmlAccessType.FIELD)
public class Production {
	
	private Build build = null;
	private Set<String> xsdVersionString = null;
	
	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
	}

	public Production() {}
	
	public Set<String> getXsdVersionString() {
		if (xsdVersionString == null) {
			xsdVersionString = new HashSet<String>();
			xsdVersionString.add(build.getXsdVersionString());
		}
		return xsdVersionString;
	}
	public Set<String> getMappingFileVersionString() {
		HashSet<String> result = new HashSet<String>();
		result.add(build.getMappingFileVersionString());
		return result;
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Production.class).createMarshaller();
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
