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
 * A simple data container for all of the XSDs versions, the production version and all of the versions 
 * that are currently in our development cycle. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "xsdVersions")
@XmlAccessorType(XmlAccessType.FIELD)
public class XsdVersions {
	
	private Production production = null;
	private Development development = null;
	private Set<String> xsdVersionStrings = null;
	private Set<String> mappingFileVersionStrings = null;
	public XsdVersions() {}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public Development getDevelopment() {
		return development;
	}

	public void setDevelopment(Development development) {
		this.development = development;
	}
	
	public Set<String> getXsdVersionStrings() {
		if (xsdVersionStrings == null) {
			xsdVersionStrings = new HashSet<String>();
			xsdVersionStrings.addAll(production.getXsdVersionString());
			xsdVersionStrings.addAll(development.getXsdVersionStrings());
		}
		return xsdVersionStrings;
		
	}
	public Set<String> getMappingFileVersionStrings() {
		if (mappingFileVersionStrings == null) {
			mappingFileVersionStrings = new HashSet<String>();
			mappingFileVersionStrings.addAll(production.getMappingFileVersionString());
			mappingFileVersionStrings.addAll(development.getMappingFileVersionStrings());
		}
		return mappingFileVersionStrings;
	}
	
	/**
	 * Returns true if the given this object contains the given <code>someXsdVersion</code>. 
	 * 
	 * @param some xsd version string.
	 */
	public boolean containsAnXsdVersionedString(String someXsdVersion) {
		return getXsdVersionStrings().contains(someXsdVersion);
	}
	
	/**
	 * Returns true if the given <code>someXsdVersion</code> is the production version of 
	 * the XSDs. 
	 * 
	 * @param someXsdVersion some xsd version string. 
	 */
	public boolean isXsdVersionStringProduction(String someXsdVersion) {
		return production.getXsdVersionString().contains(someXsdVersion);
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(XsdVersions.class).createMarshaller();
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
