package org.nch.bcr.biotab.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlException;
import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;


/**
 * 
 * @author John Deardurff 
 */
public class XMLFileLoader {

	private static void showParticleInfo(SchemaParticle particle) {
		if(particle.getParticleType() == SchemaParticle.SEQUENCE  ||
				particle.getParticleType() == SchemaParticle.CHOICE ||
				particle.getParticleType() == SchemaParticle.ALL) {
				  
			SchemaParticle[] children = particle.getParticleChildren();
			for(int i=0; i<children.length; i++) {
				// recursive - to find element definitions with xs:sequence
				showParticleInfo(children[i]); 
			
			}
		} else if (particle.getParticleType() == SchemaParticle.ELEMENT) {
			// register the newly found element
			SchemaType childSchemaType = particle.getType();
			System.err.println("*******" + childSchemaType);
		}
	}
	
	      
	public static org.apache.xmlbeans.XmlObject parseClinicalXML(JobDTO jobDTO, File xmlFile) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			ClassNotFoundException, InstantiationException,
			NoSuchMethodException, SecurityException, XmlException, IOException {
		
		//return (org.apache.xmlbeans.XmlObject) nci.tcga.bcr.xml.xmlbeans.clinical.blca_2_6_2.TcgaBcrDocument.Factory.parse(xmlFile);
	
		org.apache.xmlbeans.XmlObject result = null;
		String packageName = null;
		switch (jobDTO.getXmlFileType()) {
			case CLINICAL: 
				packageName = "nci.tcga.bcr.xml.xmlbeans.clinical." + jobDTO.getDiseaseCode().toLowerCase() + jobDTO.getXSDVersionNumberForXMLBeansPackageName();
				break;
			case OMF: 
				packageName = "nci.tcga.bcr.xml.xmlbeans.clinical.omf" + jobDTO.getXSDVersionNumberForXMLBeansPackageName();
				break;
			case SSF:
				packageName = "nci.tcga.bcr.xml.xmlbeans.ssf" + jobDTO.getXSDVersionNumberForXMLBeansPackageName();
				break;
			case PPS:
				packageName = "nci.tcga.bcr.xml.xmlbeans.prescreen" + jobDTO.getXSDVersionNumberForXMLBeansPackageName();
				break;
		}
		//String packageName = "nci.tcga.bcr.xml.xmlbeans.clinical." + jobDTO.getDiseaseCode().toLowerCase() + jobDTO.getXSDVersionNumberForXMLBeansPackageName();
		String parseMethod = "parse";
		
		Class<?> tcgaDocFactoryClass = getXmlBeansClassForName(packageName+"."+"TcgaBcrDocument", "Factory", true);
		Method newInstanceMethodObj = tcgaDocFactoryClass.getDeclaredMethod(parseMethod, new Class[] {java.io.File.class});
		result = (org.apache.xmlbeans.XmlObject)newInstanceMethodObj.invoke(null, (Object[])(new  java.io.File[] {xmlFile}) );
		
	
		return result;
	
	}
	
	public static Class<?> getXmlBeansClassForName(String packageName, String className, boolean innerClass) throws ClassNotFoundException {
		String seperator = innerClass ? "$" : ".";
		String fullClassName = packageName + seperator + className;
		return Class.forName(fullClassName);
	}

	/**
	 * Returns the patient barcode from an XML file.
	 *
	 * This method assumes a *.THEBARCODE.xml filename and the barcode must not contain periods.
	 *
	 * @param xmlFile The file from which the barcode should be extracted.
	 * @return the patient barcode from the XML file.
     */
	public static String getTcgaNumber(File xmlFile) {
		String[] fileNameParts = xmlFile.getName().split("\\.");
		return fileNameParts[fileNameParts.length - 2];
	}
}
