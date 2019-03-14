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
@XmlRootElement(name = "DisplayMetadataItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class DisplayMetadataItem {
	
	@XmlAttribute
	private String diseaseCode = "";
	
	@XmlAttribute
	private String formType = "";
	
	@XmlAttribute
	private String cde = "";
	
	@XmlAttribute
	private String preferredName = "";
	
	@XmlAttribute
	private Integer displayOrder = 999;
	
	
	public DisplayMetadataItem() {}

	public String getCde() {
		return cde;
	}

	public void setCde(String cde) {
		this.cde = cde;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	public DisplayMetadataKey getDisplayMetadataKey() {
		return new DisplayMetadataKey(diseaseCode, formType, cde);
	}
	
	@Override
	public int hashCode() {
		return  getDisplayMetadataKey().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof DisplayMetadataItem) {
			result = this.getDisplayMetadataKey().equals( ((DisplayMetadataItem)obj ).getDisplayMetadataKey() );
		}
		return result;
	}

	public String toXmlString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(DisplayMetadataItem.class).createMarshaller();
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
