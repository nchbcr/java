package org.nch.bcr.biotab.config;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * We are building up xpaths as we navigate XSDs to build instances of <code>Header</code>. The xpath format that we use is:
 * 
 * //*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='follow_ups' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='follow_up' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/followup/2.7/1.0']
 * //*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/2.7']/*[local-name()='new_tumor_events' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/shared/new_tumor_event/2.7/1.0']/*[local-name()='new_tumor_event' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/brca/shared/new_tumor_event/2.7/1.0']
 * //*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/read/2.7']/*[local-name()='clinical_cqcf' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/shared/coad_read/2.7']
 * 
 * This is a class that contains what is essentially the QName for one of the elements within the path. The full path is a list of this as defined
 * by <code>XPathPartsList</code>.
 * 	
 * @author John Deardurff
 *
 */
@XmlRootElement(name = "xPathPart")
@XmlAccessorType(XmlAccessType.FIELD)
public class XPathPart {

	@XmlAttribute
	protected String localName = "";
	
	@XmlAttribute
	protected String namespaceUri = "";
	
	public XPathPart() {}
	
	public XPathPart(String localName, String namespaceUri) {
		super();
		this.localName = localName;
		this.namespaceUri = namespaceUri;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getNamespaceUri() {
		return namespaceUri;
	}

	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}
	
	public static XPathPart createInstance(String qname) {
		Pattern qnameParts = Pattern.compile("'.*?'");
		Matcher qnamePartsMatcher = qnameParts.matcher(qname);
		qnamePartsMatcher.find();
		String localName = qnamePartsMatcher.group();
		qnamePartsMatcher.find();
		String uri = qnamePartsMatcher.group();
		XPathPart xPathPart = new XPathPart(localName.replace("'", ""), uri.replace("'", ""));
		return xPathPart;
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(XPathPart.class).createMarshaller();
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
	
	@Override
	public int hashCode() {
		return localName.hashCode() + namespaceUri.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XPathPart) {
			if ( ((XPathPart)obj).getLocalName().equals(this.getLocalName()) && ((XPathPart)obj).getNamespaceUri().equals(this.getNamespaceUri()) ) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Provides a way to compare two XPaths that may or may not include a proprietary wildcard scheme.  
	 * <P>
	 * 
	 * For example, the following two would be considered equivalent under this scheme. 
	 * 
	 *  [local-name()='follow_up' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/blca/followup/2.7/4.0']
	 *  [local-name()='follow_up' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/blca/followup/2.7/*']
	 * 
	 * This scheme only allows wildcards in the namespace-uri and the only wildcard permitted in the * character. However, there can be more then one 
	 * wildcard present in a single namespace-uri. 
	 * 
	 * </P>
	 */
	public boolean equivalent(XPathPart xPathPart) {
		boolean result = false;
		
		if (xPathPart.equals(this)) {
			result = true;
		} else if (xPathPart.getLocalName().equals(this.getLocalName())) {
			String _namespaceUri = xPathPart.getNamespaceUri().replace("*", ".+?");
			result = Pattern.matches(_namespaceUri, this.getNamespaceUri());
			
			if (!result) {
				// Now lets check the other way.
				_namespaceUri = this.getNamespaceUri().replace("*", ".+?");
				result = Pattern.matches(_namespaceUri, xPathPart.getNamespaceUri());
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
		XPathPart partsA = new XPathPart("follow_up", "XX*YYY*Z");
		XPathPart partsB = new XPathPart("follow_up", "XXAAAYYYBZ");
		XPathPart partsC = new XPathPart("follow_up", "XXYYYZ");
		
		System.err.println(partsA.equivalent(partsB));
		System.err.println(partsB.equivalent(partsA));
		System.err.println(partsC.equivalent(partsA));
		System.err.println(partsA.equivalent(partsC));
		
		System.err.println(partsB.equivalent(partsC));
		System.err.println(partsC.equivalent(partsC));
		
	}
	
	

}
