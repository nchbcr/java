package org.nch.bcr.biotab.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.config.SectionToFileMapNode;
import org.nch.bcr.biotab.domain.table.Header;
import org.nch.bcr.biotab.domain.table.HeaderCell;

/**
 * Mo utils.
 * 
 * @author John Deardurff 
 */
public class XMLBeansXSDUtils extends XMLBeansXSDUtilsInternal {

	
	public static SchemaParticle getSchemaParticleForSection(JobDTO jobDTO, SectionToFileMapNode section, XmlObject clinincalRoot) {
		SchemaParticle result = null;
		SchemaType schemaType = clinincalRoot.schemaType(); // D=tcga_bcr@http://tcga.nci/bcr/xml/clinical/blca/2.7
		SchemaParticle schemaParticle = schemaType.getContentModel();
		String xpath_root =  "/" + XMLBeansXSDUtilsBasic.getXPathFromSchemaParticle(schemaParticle);
	
		SchemaType[] anonTypes = schemaType.getAnonymousTypes();
		for (SchemaType type : anonTypes) {
	
			SchemaParticle[] tcga_bcr_children = type.getContentModel().getParticleChildren();
			for (SchemaParticle tcga_bcr_child : tcga_bcr_children) {
				
				if (tcga_bcr_child.getName().getLocalPart().equals("patient")) {
					
					if ( section.getSectionId().equals("patient")) {
						result = tcga_bcr_child;
					} else {
						result = _getSchemaParticleForSection(jobDTO, section, tcga_bcr_child, XMLBeansXSDUtilsBasic.composeXPath(xpath_root, tcga_bcr_child));
					}
				}
			}
		}
		return result;
	}
	
	
	private static SchemaParticle _getSchemaParticleForSection(JobDTO jobDTO, SectionToFileMapNode section, SchemaParticle schemaParticle, String parentXPath) {
		SchemaParticle result = null;
		
		if (schemaParticle.getParticleType() == SchemaParticle.ELEMENT) {
			
			if ( schemaParticle.getType().getContentModel() == null) {	
				// DO NOTHING since this is a single element with no children. All sections/biotabs will have child elements. 
				//System.err.println("YIKES");
				//result =  _getSchemaParticleForSection(jobDTO, section, schemaParticle.getType().getContentModel(), parentXPath);
				
			} else {
				SchemaParticle[] x_children = schemaParticle.getType().getContentModel().getParticleChildren();
				if (x_children != null) {
					for (SchemaParticle x_child : x_children) {
						if (x_child.getParticleType() != SchemaParticle.CHOICE) {			
							String xpath = XMLBeansXSDUtilsBasic.composeXPath(parentXPath, x_child);
						
							if ( x_child.getType().getContentModel() != null) {
								if (xpath.equals(section.getContainerXPath())) {
									return x_child;
								} else {
									result = _getSchemaParticleForSection(jobDTO, section, x_child, xpath);
									if (result != null) {
										return result;
									}
								}
							}
						} else {
							// DO NOTHING since a choice element will never be in its own biotab.
						}
					}
				} else {
					// This is the case of sequences with only one element. W3C specs for XSDs calls this case "pointless occurrences of <sequence>"
					// For more information on the spec see '3.9.6 Constraints on Particle Schema Components' within http://www.w3.org/TR/xmlschema-1/. 
					// 
					// The upshot of this W3C spec is that XMLBeans -- when compiling the XSDs -- will not manifest sequences that only have a single child as
					// a particle of type SEQUENCE. Rather, it filters out the SEQUENCE aspect and include just the single child as type ELEMENT. Therefore 
					// schemaParticle.getParticleChildren() returns NULL for sequences with only one child. 
					//
					// The XMLBeans implementation of this spec is located in StscComplexTypeResolver.filterPointlessParticlesAndVerifyAllParticles() at line 1498. 
					// Within this XMLBeans class, the following two lines can be commented out to lift this spec: 
					//
					//			if (part.isSingleton() && part.countOfParticleChild() == 1)
                    //				return part.getParticleChild(0);
                    //
					// Again, it is important to note that this "pointless occurrences of <sequence>" implementation is part of the XMLBeans compiler. 
					// So, for it to have effect, all of the XSD would need to be recompiled against the XSDs.
					//
					String xpath =  parentXPath + XMLBeansXSDUtilsBasic.getXPathFromSchemaParticle(schemaParticle.getType().getContentModel());
					//System.err.println("parentXPath: " + xpath);
					if (xpath.equals(section.getContainerXPath())) {
						return schemaParticle.getType().getContentModel();
					} else {
						result = _getSchemaParticleForSection(jobDTO, section, schemaParticle.getType().getContentModel(), xpath);
						if (result != null) {
							return result;
						}
					}
					
					
					
					
				}
			}
			
		} else if (schemaParticle.getParticleType() == SchemaParticle.CHOICE) {
			// DO NOTHING since a choice element will never be in its own biotab.
			
		} else if (schemaParticle.getParticleType() == SchemaParticle.SEQUENCE) {
			// This should never happen. 
			System.err.println("(patient_child) SEQUENCE SchemaParticle: " + schemaParticle.getName());
		} 
		return result;
	}
	
	
	public static List<Header<HeaderCell>> getSectionHeaders(JobDTO jobDTO, SectionToFileMapNode section, SchemaParticle patient, String patientXPath) {
		if (section.isRepeating()) {
			return getRepeatingSectionHeader(jobDTO, section, patient, patientXPath);
		} else {
			ArrayList<Header<HeaderCell>> resultList = new ArrayList<Header<HeaderCell>>();
			resultList.add(getNonRepeatingSectionHeader(jobDTO, section, patient, patientXPath));
			return resultList;
		}
	}
	
	private static List<Header<HeaderCell>> getRepeatingSectionHeader(JobDTO jobDTO, SectionToFileMapNode section, SchemaParticle patient, String patientXPath) {
		ArrayList<Header<HeaderCell>> resultList = new ArrayList<Header<HeaderCell>>();
		SchemaParticle[] patient_children = patient.getType().getContentModel().getParticleChildren();
		if (patient_children.length == 1) {
			resultList.add(getNonRepeatingSectionHeader(jobDTO, section, patient_children[0], XMLBeansXSDUtilsBasic.composeXPath(patientXPath, patient_children[0])));
		} else {
			for (SchemaParticle patient_child : patient_children) {
				if ( (patient_child.getMaxOccurs()==null || patient_child.getMaxOccurs().intValue()!=1) ) {
					resultList.add(getNonRepeatingSectionHeader(jobDTO, section, patient_child, XMLBeansXSDUtilsBasic.composeXPath(patientXPath, patient_child)));
				}
			}
		}
		return resultList;
	}
	
	
	private static Header<HeaderCell> getNonRepeatingSectionHeader(JobDTO jobDTO, SectionToFileMapNode section, SchemaParticle patient, String patientXPath) {
		
		//log.debug(MessageFormat.format("Parsing SchemaParticle [{0}] for HEADER", patient.getName().getLocalPart()) );
		
		Header<HeaderCell> result = new Header<HeaderCell>();
		String parentName = patient.getName().getLocalPart();
		SchemaParticle[] patient_children = patient.getType().getContentModel().getParticleChildren();
		for (SchemaParticle patient_child : patient_children) {
			result.addAllHeaderCells(XMLBeansXSDUtilsInternal.processSchemaParticle(jobDTO, section, patient_child, parentName, patientXPath));
		}
		result.sort();
		return result;
	}

}
