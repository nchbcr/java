package org.nch.bcr.biotab.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaParticle;
import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.config.SectionToFileMapNode;
import org.nch.bcr.biotab.domain.table.HeaderCell;

/**
 * Mo utils.
 * 
 * @author John Deardurff 
 */
class XMLBeansXSDUtilsInternal {
	
	protected static Logger log = Logger.getLogger(XMLBeansXSDUtilsInternal.class);
	
	static List<HeaderCell> processSchemaParticle(JobDTO jobDTO,  SectionToFileMapNode section, SchemaParticle schemaParticle, String parentName, String parentXPath) {
		ArrayList<HeaderCell> result = new ArrayList<HeaderCell>(25);
		
		if (schemaParticle.getParticleType() == SchemaParticle.ELEMENT) {
			
			if ( schemaParticle.getType().getContentModel() == null) {
				
				//if (log.isEnabledFor(Priority.DEBUG)) {
				//	log.debug("(patient_child, maxOccurs=" + schemaParticle.getMaxOccurs() + ") SINGLE ELEMENT SchemaParticle: " + (schemaParticle.getName().getPrefix()==""?"NS":schemaParticle.getName().getPrefix()) + ":" + schemaParticle.getName().getLocalPart());
				//}
				if ( !section.isElementExcluded(schemaParticle.getName().getLocalPart(), parentName, jobDTO.getDiseaseCode()) ) {
					
					//log.debug("(patient_child - exluded from biotabs) SINGLE ELEMENT SchemaParticle: " + (schemaParticle.getName().getPrefix()==""?"NS":schemaParticle.getName().getPrefix()) + ":" + schemaParticle.getName().getLocalPart());
					String xPathTemp = XMLBeansXSDUtilsBasic.composeXPath(parentXPath, schemaParticle);
					
					// if disease is LUNG and xpath contains stage_event from disease namespace then skip writing elements
					if ( jobDTO.getDiseaseCode().equalsIgnoreCase("LUNG") && 
							xPathTemp.contains( "[local-name()='stage_event' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/lung/2.7']" ) )
					{
						log.warn("Intentionally Skipping LUNG 'stage_event' element/s from 'http://tcga.nci/bcr/xml/clinical/lung/2.7' namespace: " + schemaParticle.getName().getLocalPart() );
					}
					// add new headers for "eber_ish_result" and "rna_integrity" elements along with the below, when it encounters below criteria
					else if	( jobDTO.getDiseaseCode().equalsIgnoreCase("DLBC") && section.getSectionId().equals("genetic_testing_results") && 
								xPathTemp.contains("\'genetic_testing_result\'") && xPathTemp.contains("\'immunophenotypic_analysis_tested\'") )
					{
						result.add(XMLBeansXSDUtilsInternal.processSingleElement(jobDTO, section, schemaParticle, parentXPath, "eber_ish_result"));
						result.add(XMLBeansXSDUtilsInternal.processSingleElement(jobDTO, section, schemaParticle, parentXPath, "rna_integrity"));
						result.add(XMLBeansXSDUtilsInternal.processSingleElement(jobDTO, section, schemaParticle, parentXPath, null));
					}
					else
						result.add(XMLBeansXSDUtilsInternal.processSingleElement(jobDTO, section, schemaParticle, parentXPath, null));
				}
				
			} else {

				String xpath =  XMLBeansXSDUtilsBasic.composeXPath(parentXPath, schemaParticle);
				if (!jobDTO.doesXpathMapToSeperateTabFile(section, xpath)) {
				
					parentName = schemaParticle.getName().getLocalPart();
					SchemaParticle[] x_children = schemaParticle.getType().getContentModel().getParticleChildren();
					
					//log.debug("(patient_child, maxOccurs=" + schemaParticle.getMaxOccurs() + ") CONTAINER ELEMENT SchemaParticle: " + (schemaParticle.getName().getPrefix()==""?"NS":schemaParticle.getName().getPrefix()) + ":" + schemaParticle.getName().getLocalPart());
					//log.debug("------ (patient_child) CONTAINER ELEMENT SchemaType: " + schemaParticle.getType());
					//log.debug("------ (patient_child_children) SchemaParticle[].length: " + (x_children==null ? "null" : x_children.length));
					
					if (x_children != null) {
						for (SchemaParticle x_child : x_children) {

							//log.debug("------ (patient_child_child) ELEMENT SchemaType: " + x_child.getType());
							if (x_child.getParticleType() != SchemaParticle.CHOICE) {
								result.addAll(XMLBeansXSDUtilsInternal.processSchemaParticle(jobDTO, section, x_child, parentName, xpath));
							} else {
								result.addAll(XMLBeansXSDUtilsInternal.processSchemaParticle(jobDTO, section, x_child, parentName, xpath));
							}
						}
					} else {
						
						/*
						System.err.println("YIKES!");
						SchemaParticle[] y_children = schemaParticle.getParticleChildren();
						SchemaType[] anonTypes = schemaParticle.getType().getAnonymousTypes();
						schemaParticle.countOfParticleChild();
						SchemaType schemaType = schemaParticle.getType();
						anonTypes = schemaParticle.getType().getContentModel().getType().getAnonymousTypes();
						
						
						System.err.println("YIKES! ------ PARTICAL TYPE: " + schemaType.getContentModel().getParticleType() + "---" + " SEQUENCE = " + SchemaParticle.SEQUENCE + " ELEMENT = " + SchemaParticle.ELEMENT);
						System.err.println("YIKES! *********** PARTICAL Name: " + schemaType.getContentModel().getName());
						*/
						
						// 
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
						// Again, it is important to note that this spec implementation is part of the XSD compiler. So, for it to have effect, all of the XSD would 
						// need to be recompiled against the XSDs.
						//
						result.addAll(XMLBeansXSDUtilsInternal.processSchemaParticle(jobDTO, section, schemaParticle.getType().getContentModel(), parentName, xpath));
					}
				}
				
			}
			
		} else if (schemaParticle.getParticleType() == SchemaParticle.CHOICE) {
			
			//System.err.println("(patient_child) CHOICE SchemaParticle: " + schemaParticle.getName());
			// As of 2013-08-19 all CHOICE elements correspond to date elements. 
			// As of 2013-08-19 all DATE elements -- EXCEPT for date of form completion -- are PHI and are converted to a days to. 
			result.addAll(XMLBeansXSDUtilsInternal.processDate(jobDTO, section, schemaParticle, parentName, parentXPath));
			
		} else if (schemaParticle.getParticleType() == SchemaParticle.SEQUENCE) {
			// This should never happen. 
			log.debug("(patient_child) SEQUENCE SchemaParticle: " + schemaParticle.getName());
		} 
		return result;
	}
	
	static HeaderCell processSingleElement(JobDTO jobDTO, SectionToFileMapNode currentSection, SchemaParticle schemaParticle, String parentXPath, String elementName) {
		return HeaderCell.createInstance(jobDTO, currentSection, schemaParticle, parentXPath, elementName);
	}
	
	static List<HeaderCell> processDate(JobDTO jobDTO, SectionToFileMapNode currentSection, SchemaParticle schemaParticle, String parentName, String parentXPath) {
		List<HeaderCell> result = new ArrayList<HeaderCell>();
		
		SchemaParticle[] choiceParticles = schemaParticle.getParticleChildren();
		for (SchemaParticle choiceParticle : choiceParticles) {
			if (choiceParticle.getParticleType() == SchemaParticle.ELEMENT) {
				//System.err.println("------- (Choice SINGLE Particle): " + choiceParticle.getName());
				
				if (choiceParticle.getName().getLocalPart().equals("days_to_form_completion") && currentSection.getElementName().contains(parentName) ) {
                    HeaderCell hc = getDateOfFormCompletionHeaderCell(jobDTO, currentSection, parentXPath, choiceParticle);
					result.add(hc);
					
				} else if (choiceParticle.getName().getLocalPart().contains("days_to")) {
					if ( !currentSection.isElementExcluded(choiceParticle.getName().getLocalPart(), parentName, jobDTO.getDiseaseCode()) ) {
						result.add(HeaderCell.createInstance(jobDTO, currentSection, choiceParticle, parentXPath, null));
					}
				}
			} else if (choiceParticle.getParticleType() == SchemaParticle.SEQUENCE) {
				//System.err.println("------- (Choice SEQUENCE Particle): ");

				SchemaParticle[] choiceChildren = choiceParticle.getParticleChildren();
				for (SchemaParticle choiceChild : choiceChildren) {
					//System.err.println("------- (Choice SINGLE Particle from SEQUENCE): " + choiceChild.getName());
					
					// TODO: There is another study that has this origin date with a different name. Account for this other naming. 
					if (choiceChild.getName().getLocalPart().contains("days_to_initial_pathologic_diagnosis") ||
						choiceChild.getName().getLocalPart().contains("age_at_initial_pathologic_diagnosis")) {
						
						result.add(HeaderCell.createInstance(jobDTO, currentSection, choiceChild, parentXPath, null));
					}
				}
			}
		}
		return result;
	}

    static HeaderCell getDateOfFormCompletionHeaderCell(JobDTO jobDTO, SectionToFileMapNode currentSection, String parentXPath, SchemaParticle choiceParticle) {
        HeaderCell hc = new HeaderCell();
        hc.setSortByDisplayOrder(Boolean.valueOf(jobDTO.getProperty("clinical.biotab.sort.by.display.order")));
        hc.setCde("");
        hc.setElementName("");
        hc.setPreferredName("form_completion_date");
        hc.setElementName("form_completion_date");
        hc.setRepeating(false);
        hc.setDisplayOrder(-80);

        String missingYearReplacementValue = "[Not Available]";

        String xpath =
                //Do we have a year?
                "if(boolean(/*[local-name()='year_of_form_completion' and namespace-uri()='" + choiceParticle.getName().getNamespaceURI() + "']/text()))" +
                //If yes, make a date
                " then concat(" +
                    //Get the year
                    "/*[local-name()='year_of_form_completion' and namespace-uri()='" + choiceParticle.getName().getNamespaceURI() + "']" +
                    ", '-'," + //add a separator
                    //Do we have a month?
                    "if(boolean(/*[local-name()='month_of_form_completion' and namespace-uri()='" + choiceParticle.getName().getNamespaceURI() + "']/text()))" +
                        //If we do, get that element
                        " then /*[local-name()='month_of_form_completion' and namespace-uri()='" + choiceParticle.getName().getNamespaceURI() + "']" +
                        //If not replace with 0
                        " else '0'" +
                    ", '-'," + //add a separator
                    //Do we have a day?
                    "if(boolean(/*[local-name()='day_of_form_completion' and namespace-uri()='" + choiceParticle.getName().getNamespaceURI() + "']/text()))" +
                        //If we do, get that element
                        " then /*[local-name()='day_of_form_completion' and namespace-uri()='" + choiceParticle.getName().getNamespaceURI() + "']" +
                        //If not replace with 00
                        "else '00'" +
                ")" + //end concat
                " else '" + missingYearReplacementValue + "'" //Fill with something else
                ;

        if (currentSection.getSectionId().equals("patient")) {
            xpath = xpath.replace("/*[local-name()=",parentXPath + "/*[local-name()=");

        } else {
            //We need to search with positional predicate
            xpath = xpath.replace("/*[local-name()=","xpathToContainerWithPositionalPredicate/*[local-name()=");
        }

        hc.setXpath(xpath);
        return hc;
    }
}
