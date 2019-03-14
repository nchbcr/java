package org.nch.bcr.biotab.app;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;
import org.nch.bcr.biotab.clinical.app.ClinicalBiotaberApp;
import org.nch.bcr.biotab.config.SectionToFileLookupLoader;
import org.nch.bcr.biotab.config.SectionToFileLookupNode;
import org.nch.bcr.biotab.config.SectionToFileMapNode;
import org.nch.bcr.biotab.config.XPathPartsList;

/**
 * This is the general data for a working thread needs to build biotabs for a given study. 
 * <p>
 * This class is fully during the initialization phase and then handed off to a worker thread. 
 * </p>
 * <p>
 * There is no reason why the application code should mutate this class after it has been initiaized. 
 * </p>
 * 
 * @author John Deardurff 
 */
public class JobDTO implements Iterable<SectionToFileMapNode> {
	
	protected static Logger log = Logger.getLogger(JobDTO.class);
	
	protected Properties properties = null;
	protected BaseBiotaberApp.XmlFileType xmlFileType;
	protected String diseaseCode = "";
	
	protected String xsdMajorVersion = null; //the major version number of the XSDs to be used to generate XML. This parameter is required. 
	protected String xsdMinorVersion = null; //the minor version number of the XSDs to be used to generate XML. This parameter is required. 
	protected String xsdPatchNumber = null; //the patch number of the XSDs to be used to generate XML. This parameter is required. 
	
	protected SectionToFileLookupNode sectionToFileLookupNode;
	
	public JobDTO(Properties properties, BaseBiotaberApp.XmlFileType xmlFileType, String diseaseCode) throws IOException {
		this.properties = properties;
		this.xmlFileType = xmlFileType;
		this.diseaseCode = diseaseCode;
		
		xsdMajorVersion = ClinicalBiotaberApp.build.getXsdMajorVersion();
		xsdMinorVersion = ClinicalBiotaberApp.build.getXsdMinorVersion();
		xsdPatchNumber = ClinicalBiotaberApp.build.getXsdPatchNumber();
		checkVersionNumbers();
		
		sectionToFileLookupNode = SectionToFileLookupLoader.load(getXmlFileType(), getDiseaseCode());
		
		//log.trace("****************");
		//log.trace("The DSL control file:");
		//log.trace(sectionToFileLookupNode.toString());
		//log.trace("****************");
	}
	
	
	public Properties getProperties() {
		return properties;
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public BaseBiotaberApp.XmlFileType getXmlFileType() {
		return xmlFileType;
	}

	public void setXmlFileType(BaseBiotaberApp.XmlFileType xmlFileType) {
		this.xmlFileType = xmlFileType;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}
	
	public String getXSDVersionNumberForXMLBeansPackageName() {
		checkVersionNumbers();
		return "_" + xsdMajorVersion + "_" + xsdMinorVersion + "_" + xsdPatchNumber;
	}
	
	public String getXSDVersionNumberForXMLNamespace() {
		checkVersionNumbers();
		return xsdMajorVersion + "." + xsdMinorVersion;
	}
	
	public void checkVersionNumbers() {
		if (
			(xsdMajorVersion == null || xsdMajorVersion.trim().equals("")) ||
			(xsdMinorVersion == null || xsdMinorVersion.trim().equals("")) ||
			(xsdPatchNumber == null || xsdPatchNumber.trim().equals("")) 
		) {
			throw new IllegalArgumentException(MessageFormat.format(
					"All the XSD Version Numbers are required, but has not been provded. See xsdMajorVersion: [{0}], xsdMinorVersion: [{1}], xsdPatchNumber: [{2}]",
					xsdMajorVersion, xsdMinorVersion, xsdPatchNumber));
		}
	}

	@Override
    public Iterator<SectionToFileMapNode> iterator() {
        return sectionToFileLookupNode.iterator();
	}
    
	protected List<XPathPartsList> getAllContainerXPathPartsList() {
		return sectionToFileLookupNode.getAllContainerXPathPartsList();
	}

	protected List<XPathPartsList> getAllContainerXPathPartsListLessCurrent(SectionToFileMapNode currentSection) {
		List<XPathPartsList> result = getAllContainerXPathPartsList();
		result.remove(currentSection.getContainerXPathPartsList());
		return result;
	}

	public boolean doesXpathMapToSeperateTabFile(SectionToFileMapNode currentSection, XPathPartsList xPathPartsList) {
		boolean result = false;
		for (XPathPartsList containerParts : getAllContainerXPathPartsListLessCurrent(currentSection)) {
			if (containerParts.equals(xPathPartsList)) {
				return true;
			}
		}
		return result;
	}

	public boolean doesXpathMapToSeperateTabFile(SectionToFileMapNode currentSection, String xpath) {
		return doesXpathMapToSeperateTabFile(currentSection, XPathPartsList.createInstance(xpath));
	}

}
