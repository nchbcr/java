package org.nch.bcr.biotab.config;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class is the DTO for the 'sectionToFileLookup' closure. 
 * 
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "sectionToFileLookup")
@XmlAccessorType(XmlAccessType.FIELD)
public class SectionToFileLookupNode implements Iterable<SectionToFileMapNode> {
	
	@XmlElement(name = "sectionToFileMaps")
	protected List<SectionToFileMapNode> cache = new ArrayList<SectionToFileMapNode>();
	
	public SectionToFileLookupNode() {}
	
	public SectionToFileLookupNode(List<SectionToFileMapNode> cache) {
		this.cache = cache;
	}
	
	public Iterator<SectionToFileMapNode> getMaps() {
		return cache.iterator();
	}
	
	public void addSectionToFileMap(SectionToFileMapNode sectionToFileMap) {
		cache.add(sectionToFileMap);
	}
	
	@Override
    public Iterator<SectionToFileMapNode> iterator() {
        Iterator<SectionToFileMapNode> it = new Iterator<SectionToFileMapNode>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < cache.size() && cache.get(currentIndex) != null;
            }

            @Override
            public SectionToFileMapNode next() {
            	currentIndex++;
                return cache.get(currentIndex-1);
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
            }
        };
        return it;
    }
	
	public List<XPathPartsList> getAllContainerXPathPartsList() {
		List<XPathPartsList> result = new ArrayList<XPathPartsList>();
		for (SectionToFileMapNode node : cache) {
			result.add(node.getContainerXPathPartsList());
		}
		
		return result;
	}
	
	public SectionToFileMapNode getPatientSection() {
		SectionToFileMapNode result = null;
		for (SectionToFileMapNode node : cache) {
			if (node.getSectionId().trim().equalsIgnoreCase("patient")) {
				result = node;
				break;
			}
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
			marshaller = JAXBContext.newInstance(SectionToFileLookupNode.class).createMarshaller();
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
