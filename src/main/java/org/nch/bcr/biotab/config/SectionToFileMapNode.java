package org.nch.bcr.biotab.config;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'sectionToFileMap' closure. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "sectionToFileMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class SectionToFileMapNode {
	
	@XmlElement(name = "sectionId")
	private String sectionId = "";
	
	@XmlElement(name = "parentSectionId")
	private String parentSectionId = "";
	
	@XmlElement(name = "formType")
	private String formType = "";
	
	@XmlElement(name = "elementName")
	private String elementName = "";

	@XmlElement(name = "containerXPath")
	private String containerXPath = "";
	
	@XmlTransient
	private XPathPartsList containerXPathPartsList = null;
	
	@XmlElement(name = "repeating")
	private boolean repeating = true;
	
	@XmlElement(name = "biotabFilename")
	private String biotabFilename = "";
	
	@XmlElement(name = "elementExclusionListNode")
	private ElementExclusionListNode elementExclusionListNode = null;
	
	
	public SectionToFileMapNode() {}
	
	public SectionToFileMapNode(String sectionId, String parentSectionId, String formType, 
			String elementName, String containerXPath, boolean repeating, String biotabFilename) {
		super();
		this.sectionId = sectionId;
		this.parentSectionId = parentSectionId;
		this.formType = formType;
		this.elementName = elementName;
		this.containerXPath = containerXPath;
		this.repeating = repeating;
		this.biotabFilename = biotabFilename;

		containerXPathPartsList = XPathPartsList.createInstance(this.containerXPath);
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getParentSectionId() {
		return parentSectionId;
	}

	public void setParentSectionId(String parentSectionId) {
		this.parentSectionId = parentSectionId;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getContainerXPath() {
		return containerXPath;
	}

	public void setContainerXPath(String containerXPath) {
		this.containerXPath = containerXPath;
	}
	
	public XPathPartsList getContainerXPathPartsList() {
		return containerXPathPartsList;
	}

	public boolean isRepeating() {
		return repeating;
	}

	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}

	public String getBiotabFilename() {
		return biotabFilename;
	}

	public void setBiotabFilename(String biotabFilename) {
		this.biotabFilename = biotabFilename;
	}
	
	public ElementExclusionListNode getElementExclusionListNode() {
		return elementExclusionListNode;
	}

	public void setElementExclusionListNode(
			ElementExclusionListNode elementExclusionListNode) {
		this.elementExclusionListNode = elementExclusionListNode;
	}
	
	public boolean isElementExcluded(String elementName, String parentName, String diseaseCode) {
		return ( getElementExclusionListNode()==null ? false : getElementExclusionListNode().isExcluded(elementName, parentName, diseaseCode) );
	}

	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(SectionToFileMapNode.class).createMarshaller();
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
