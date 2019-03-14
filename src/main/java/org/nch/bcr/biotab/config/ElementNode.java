package org.nch.bcr.biotab.config;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'element' closure. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "element")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElementNode {
	
	@XmlAttribute
	protected String name = "";
	
	@XmlAttribute
	protected String parentName = null;
	
	protected List<String> parentList = null;
	
	protected ConditionNode conditionNode = null;
	
	public ElementNode() {}
	
	public ElementNode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public List<String> getParentList() {
		parentList = new ArrayList<String>();
		if (parentName != null) {
			String[] parts = parentName.split(":");
			
			for ( String part : parts )
				parentList.add( part.trim() );
		}
		return parentList;
	}
	
	public ConditionNode getConditionNode() {
		return conditionNode;
	}

	public void setConditionNode(ConditionNode conditionNode) {
		this.conditionNode = conditionNode;
	}
	
	public boolean isExcluded(String diseaseCode) {
		return conditionNode == null ? true : conditionNode.isSatisfied(diseaseCode);
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(ElementNode.class).createMarshaller();
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
