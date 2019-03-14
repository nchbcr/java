package org.nch.bcr.biotab.config;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'condition' closure. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "condition")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConditionNode<T extends Criteria> {
	
	//@XmlAttribute
	@XmlTransient
	protected T criteria = null;

	public ConditionNode() {}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(T criteria) {
		this.criteria = criteria;
	}
	
	public boolean isSatisfied(String diseaseCode) {
		return criteria.test(diseaseCode);
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(ConditionNode.class).createMarshaller();
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
