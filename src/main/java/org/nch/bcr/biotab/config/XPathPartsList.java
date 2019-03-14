package org.nch.bcr.biotab.config;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * We are building up xpaths as we navigate XSDs to build instances of <code>Header</code>. The xpath format that we use is:
 * 
 * //*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='follow_ups' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='follow_up' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/followup/2.7/1.0']
 * //*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/2.7']/*[local-name()='new_tumor_events' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/shared/new_tumor_event/2.7/1.0']/*[local-name()='new_tumor_event' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/shared/new_tumor_event/2.7/1.0']
 * //*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='clinical_cqcf' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/shared/coad_read/2.7']
 * 
 * This is a class that contains what is essentially list of QNames -- one for each element within the path. The full path is a list of <code>XPathPart</code> as defined
 * by <code>XPathPartsList</code>.
 * 	
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "xPathPartsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class XPathPartsList {
	
	@XmlElementWrapper(name="xPathParts")
	@XmlElement(name = "xPathPart")
	private List<XPathPart> xPathPartsList = null;
	
	public XPathPartsList() {}
	
	private XPathPartsList(List<XPathPart> myList) {
		this.xPathPartsList = myList;
	}
	
	public int size() {
		return xPathPartsList.size();
	}
	
	public XPathPart getXPathPartAt(int i) {
		return xPathPartsList.get(i);
	}
	
	public static XPathPartsList createInstance(String xpath) {
		List<XPathPart> myList = new ArrayList<XPathPart>(5);
		
		Pattern qnames = Pattern.compile("\\[.*?\\]");
		Matcher qnameMatcher = qnames.matcher(xpath);
		while (qnameMatcher.find()) {
			String part = qnameMatcher.group();
			XPathPart xPathPart = XPathPart.createInstance(part);
			myList.add(xPathPart);
		}
		
		return new XPathPartsList(myList);
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(XPathPartsList.class).createMarshaller();
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
	
	public boolean containsStrictly(XPathPart xPathParts) {
		return xPathPartsList.contains(xPathParts);
	}
	
	public boolean containsEquivalently(XPathPart xPathParts) {
		for (XPathPart aPart : xPathPartsList) {
			if (aPart.equivalent(xPathParts)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		for (XPathPart parts : xPathPartsList) {
			result += parts.hashCode();
		}
		return result;
	}
	
	@Override
	public boolean equals(Object xPathPartsList) {
		boolean result = false;
		if (xPathPartsList instanceof XPathPartsList) {
			XPathPartsList _xPathPartsList = (XPathPartsList)xPathPartsList;
			if (_xPathPartsList.size() == this.size()) {
				for (int i=0; i<_xPathPartsList.size(); i++) {
					if ( !_xPathPartsList.getXPathPartAt(i).equals(this.getXPathPartAt(i)) ) {
						return false;
					}
				}
				result = true;
			}
		}
		return result;
	}
	
	
	public boolean equivalent(XPathPartsList xPathPartsList) {
		boolean result = false;
		if (xPathPartsList.size() == this.size()) {
			for (int i=0; i<xPathPartsList.size(); i++) {
				if ( !xPathPartsList.getXPathPartAt(i).equivalent(this.getXPathPartAt(i)) ) {
					break;
				}
			}
			result = true;
		}
		return result;
	}
	
	/**
	 * Determines whether this is an immediate child of the given <code>xPathPartsList</code>. 
	 * 
	 */
	public boolean immediateChildOfStrictly(XPathPartsList xPathPartsList) {
		boolean result = false;
		if (this.size() > 1) {
			if (this.size()-1 == xPathPartsList.size() ) {
				for (int i=0; i<xPathPartsList.size(); i++) {
					if ( !xPathPartsList.getXPathPartAt(i).equals(this.getXPathPartAt(i)) ) {
						break;
					}
				}
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Determines whether this is an immediate child of the given <code>xPathPartsList</code>. 
	 * 
	 */
	public boolean immediateChildOfEquivalently(XPathPartsList xPathPartsList) {
		boolean result = false;
		if (this.size() > 1) {
			if (this.size()-1 == xPathPartsList.size() ) {
				for (int i=0; i<xPathPartsList.size(); i++) {
					if ( !xPathPartsList.getXPathPartAt(i).equivalent(this.getXPathPartAt(i)) ) {
						break;
					}
				}
				result = true;
			}
		}
		
		return result;
	}
	
	
	/**
	 * This is for quick and dirty development testing. 
	 * 
	 * TODO: This needs to be replaced with one or more unit tests. 
	 *
	 */
	public static void main(String[] args) {
		XPathPartsList test = XPathPartsList.createInstance("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/blca/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/blca/2.7']/*[local-name()='new_tumor_events' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/blca/shared/new_tumor_event/2.7/1.0']");
		System.err.println("XPathPartsList: ");
		System.err.println(test);
	
	}
}
