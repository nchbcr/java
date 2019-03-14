package org.nch.bcr.biotab.versions;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains the XSD versions that are in our current build cycle. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "development")
@XmlAccessorType(XmlAccessType.FIELD)
public class Development {
	
private List<Build> builds = new ArrayList<Build>();
	
	public Development() {}

	public List<Build> getBuilds() {
		return builds;
	}

	public void setBuilds(List<Build> builds) {
		this.builds = builds;
	}
	
	public void addBuild(Build build) {
		this.builds.add(build);
	}	
	
	public Set<String> getXsdVersionStrings() {
		HashSet<String> result = new HashSet<String>();
		for (Build build : builds) {
			result.add(build.getXsdVersionString());
		}
		return result;
	}
	public Set<String> getMappingFileVersionStrings() {
		HashSet<String> result = new HashSet<String>();
		for (Build build : builds) {
			result.add(build.getMappingFileVersionString());
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Development.class).createMarshaller();
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
