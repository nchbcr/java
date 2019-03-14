package org.nch.bcr.biotab.config;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'notIn' and 'In' closures. 
 * 
 * @author John Deardurff
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Criteria {

	@XmlAttribute
	protected String diseaseList  = "";
	
	@XmlTransient
	private Map<String, Boolean> diseases = new HashMap<String, Boolean>();
	
	public Criteria() {}
	
	public Criteria(String diseaseList) {
		this.setDiseaseList(diseaseList);
	}

	public String getDiseaseList() {
		return diseaseList;
	}

	public void setDiseaseList(String diseaseList) {
		this.diseaseList = diseaseList;
		if (diseaseList != null) {
			if (!diseaseList.trim().equals("")) {
				String[] parts = diseaseList.split(":");
				for (String part : parts) {
					diseases.put(part, new Boolean(true));
				}
			}
		}
	}
	
	public Map<String, Boolean> getDiseases() {
		return diseases;
	}

	public void setDiseases(Map<String, Boolean> diseases) {
		this.diseases = diseases;
	}

	public abstract boolean test(String diseaseCode);
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Criteria.class).createMarshaller();
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
