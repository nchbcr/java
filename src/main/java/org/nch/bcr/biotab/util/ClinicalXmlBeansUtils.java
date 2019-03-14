package org.nch.bcr.biotab.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.xmlbeans.XmlObject;
import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;

/**
 * Mo utils.
 * 
 * @author John Deardurff 
 */
public class ClinicalXmlBeansUtils {
	
	public static String getTcgaBarcode(XmlObject root, String diseaseCode, String xsdVersion) {
		String result = null;
		
		String xpath = "//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*[local-name()='bcr_patient_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/shared/" + xsdVersion + "']";
		XmlObject[] children = root.selectPath(xpath);
		if (children.length > 0) {
			result = XMLBeansUtils.extractElementTextAsString(children[0]);
		}
		return result;
	}
	
	
	/**
	 * extract an array of XML elements identified by their qname from an XML source 
	 * document. 
	 * 
	 * @param source - the xml fragment
	 * @param q - QName of elements to extract
	 * @param asClass - the XMLbeans class of the elements to extract
	 * @return List<T> - a list of XMLBeans objects with the correct runtime type
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T>extractAllPatientElements(XmlObject source, String diseaseCode, String xsdVersion) {
		
		List<T>results=new ArrayList<T>();

		String xpath = "//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*";
		
		XmlObject[] children = source.selectPath(xpath);
		
		for (XmlObject child : children) {	
			
			if ( !XMLBeansUtils.getQNameFor(child).getLocalPart().equals("follow_up") &&
					!XMLBeansUtils.getQNameFor(child).getLocalPart().equals("radiations") &&
					!XMLBeansUtils.getQNameFor(child).getLocalPart().equals("drug")) {
				
				// TODO: Make sure that the element is NOT in the exclusion list where the exclusion list is 
				// defined in a properties file/ XML File / or Swing config file.
				results.add((T)child);
			}
		}
		return results;
	}
	
	
	public static XmlObject getPatient(XQueryUtil xQuery, XmlObject root, String diseaseCode, String xsdVersion) {
		String xpath = "//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']";
		XmlObject[] children = root.selectPath(xQuery.appendNamespaceDeclarationForXQuery(xpath));
		return children[0];
	}
	
	public static List<XmlObject> getFollowups(XQueryUtil xQuery, XmlObject root, String diseaseCode, String xsdVersion) {
		List<XmlObject> result = new ArrayList<XmlObject>();
		
		List<String> aliases = xQuery.getNamespaceAliases();
		for (String alias : aliases) {
			if (alias.contains("follow_up")) {
				
				String xpath = "//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']/*[local-name()='follow_ups' and namespaces-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/" + xsdVersion + "']";
			
				XmlObject[] children = root.selectPath(xQuery.appendNamespaceDeclarationForXQuery(xpath));
				result.addAll( Arrays.asList(children) );

			}
		}
		return result;
	}
}
