package org.nch.bcr.biotab.domain.cache;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Cache of tblClinicalCrfDataDictionary
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "DisplayMetadataKey")
@XmlAccessorType(XmlAccessType.FIELD)
public class DisplayMetadataKey {
	
	@XmlAttribute
	private String diseaseCode = "";
	
	@XmlAttribute
	private String formType = "";
	
	@XmlAttribute
	private String cde = "";
	
	public DisplayMetadataKey() {}
	
	public DisplayMetadataKey(String diseaseCode, String formType, String cde) {
		super();
		this.diseaseCode = diseaseCode.toUpperCase();
		this.formType = formType;
		this.cde = cde;
	}
	
	public String getDiseaseCode() {
		return diseaseCode;
	}
	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode.toUpperCase();
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getCde() {
		return cde;
	}
	public void setCde(String cde) {
		this.cde = cde;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof DisplayMetadataKey) {
			result = this.toString().equalsIgnoreCase( ((DisplayMetadataKey)obj ).toString() );
		}
		return result;
	}
	
	@Override
	public String toString() {
		return diseaseCode + ":" + formType + ":" + cde;
	}
	
	public String toXmlString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(DisplayMetadataKey.class).createMarshaller();
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
