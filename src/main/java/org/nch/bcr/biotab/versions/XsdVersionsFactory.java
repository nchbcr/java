package org.nch.bcr.biotab.versions;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;


/**
 * Factory class for creating an instance of XsdVersions.
 * 
 * @author John Deardurff
 *
 */
public class XsdVersionsFactory {
	
	/**
	 * For adhoc testing only
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			//XsdVersions xsdVersions = XsdVersionsFactory.createInstance("C://Development//projects_2.5//openclinca-clinical-data-extraction//branches//xsd-2.5.3//src//main//templates//xsdVersions.xml");
			InputSource versionFileStream = new InputSource((new String()).getClass().getResourceAsStream("/xsdVersions.xml"));
			XsdVersions xsdVersions = XsdVersionsFactory.createInstance(versionFileStream);
			System.out.println(xsdVersions.toString());
			
		} catch (XsdVersionFileException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static XsdVersions createInstance(InputSource versionFileStream) throws XsdVersionFileException {
		XsdVersions result = new XsdVersions();
		
		try {
			SAXBuilder builder = new SAXBuilder();
			//InputSource versionFileStream = new InputSource(new FileInputStream(versionFilePath));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			dbFactory.setIgnoringElementContentWhitespace(true);
			Document versionDoc = builder.build(versionFileStream); 
			Element root = versionDoc.getRootElement();
			Element productionElement = root.getChild("production");
			Production productionDTO = new Production();
			Build productionBuildDTO = new Build();
			productionBuildDTO.setXsdMajorVersion(productionElement.getChild("build").getChild("xsdMajorVersion").getText());
			productionBuildDTO.setXsdMinorVersion(productionElement.getChild("build").getChild("xsdMinorVersion").getText());
			productionBuildDTO.setXsdPatchNumber(productionElement.getChild("build").getChild("xsdPatchNumber").getText());
			productionBuildDTO.setDslBuildNumber(productionElement.getChild("build").getChild("dslBuildNumber").getText());
			productionDTO.setBuild(productionBuildDTO);
			result.setProduction(productionDTO);
			
			Development developmentDTO = new Development();
			Element developmentElement = root.getChild("development");
			List<Element> developmentBuilds = developmentElement.getChildren("build");
			for (Element build : developmentBuilds) {
				Build developmentBuildDTO = new Build();
				developmentBuildDTO.setXsdMajorVersion(build.getChild("xsdMajorVersion").getText());
				developmentBuildDTO.setXsdMinorVersion(build.getChild("xsdMinorVersion").getText());
				developmentBuildDTO.setXsdPatchNumber(build.getChild("xsdPatchNumber").getText());
				developmentBuildDTO.setDslBuildNumber(build.getChild("dslBuildNumber").getText());
				developmentDTO.addBuild(developmentBuildDTO);
			}
			result.setDevelopment(developmentDTO);
		
		} catch (Throwable e) {
			throw new XsdVersionFileException("Could not parse xsd version control file", e);
		}
		return result;
	}
	
	

}
