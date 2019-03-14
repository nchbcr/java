package org.nch.bcr.biotab.config;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'elementExclusionList' closure. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "elementNodeList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElementExclusionListNode {
	
	@XmlElement(name = "elementNodeList")
	protected List<ElementNode> elementNodeList = new ArrayList<ElementNode>(10);
	
	public ElementExclusionListNode() {}

	public List<ElementNode> getElementNodeList() {
		return elementNodeList;
	}

	public void setElementNodeList(List<ElementNode> elementNodeList) {
		this.elementNodeList = elementNodeList;
	}
	
	public void addElementNode(ElementNode node) {
		elementNodeList.add(node);
	}
	
	public boolean contains(String elementName, String parentName) {
		for (ElementNode node : elementNodeList) {
			if( node.getParentName() != null ? node.getName().equalsIgnoreCase(elementName) && node.getParentList().stream().anyMatch(parent -> parent.equalsIgnoreCase(parentName)) //node.getParentName().equalsIgnoreCase(parentName) 
					: node.getName().equalsIgnoreCase(elementName) )
				return true;
			
		}
		return false;
	}
	
	public boolean isExcluded(String elementName, String parentName, String diseaseCode) {
		for (ElementNode node : elementNodeList) {
			if( node.getParentName() != null ? node.getName().equalsIgnoreCase(elementName) && node.getParentList().stream().anyMatch(parent -> parent.equalsIgnoreCase(parentName)) //node.getParentName().equalsIgnoreCase(parentName) 
					: node.getName().equalsIgnoreCase(elementName) )
				return node.isExcluded(diseaseCode);
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(ElementExclusionListNode.class).createMarshaller();
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
