package org.nch.bcr.biotab.versions;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Data beans that simply contains the version information of an XSD release. 
 *
 * @author John Deardurff
 */
@XmlRootElement(name = "build")
@XmlAccessorType(XmlAccessType.FIELD)
public class Build {
	
	private String xsdMajorVersion = "";
	private String xsdMinorVersion = "";
	private String xsdPatchNumber = "";
	private String dslBuildNumber = "";
	
	public Build() {}
	
	public String getXsdMajorVersion() {
		return xsdMajorVersion;
	}
	public void setXsdMajorVersion(String xsdMajorVersion) {
		this.xsdMajorVersion = xsdMajorVersion;
	}
	public String getXsdMinorVersion() {
		return xsdMinorVersion;
	}
	public void setXsdMinorVersion(String xsdMinorVersion) {
		this.xsdMinorVersion = xsdMinorVersion;
	}
	public String getXsdPatchNumber() {
		return xsdPatchNumber;
	}
	public void setXsdPatchNumber(String xsdPatchNumber) {
		this.xsdPatchNumber = xsdPatchNumber;
	}
	public String getDslBuildNumber() {
		return dslBuildNumber;
	}
	public void setDslBuildNumber(String dslBuildNumber) {
		this.dslBuildNumber = dslBuildNumber;
	}
	public String getXsdVersionString() {
		return xsdMajorVersion + "." + xsdMinorVersion + "." + xsdPatchNumber;
	}
	public String getMappingFileVersionString() {
		return getXsdVersionString() + "." + dslBuildNumber;
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Build.class).createMarshaller();
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
