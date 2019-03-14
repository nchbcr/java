package org.nch.bcr.biotab.domain.table;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.nch.bcr.biotab.app.BioTabFileDTO;
import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataCache;
import org.nch.bcr.biotab.util.XMLBeansUtils;
import org.nch.bcr.biotab.util.XQueryUtil;

/**
 * This is a DTO for all data that goes into a row of a tab-delimited file, which consists 
 * of at least a list of a list of <Cell> objects. 
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "row")
@XmlAccessorType(XmlAccessType.FIELD)
public class Row<T> implements DiscrepancyResolvable<DisplayMetadataCache>  {
	
	protected static Logger log = Logger.getLogger(Row.class);
	
	@XmlAttribute
	private String tcgaBarcode = "";
	
	//@XmlTransient // Mark transient so it does not get serialized to XML.
	@XmlElement(name = "cells")
	protected HashMap<HeaderCell, Cell> cellLookup = new HashMap<HeaderCell, Cell>();
	
	@XmlAttribute
	private boolean resolved = false;
	
	// hard coded props to write "eber_genetic_testing_result" section data as part of "genetic_testing_result"(PPS) biotab file
	private static List<String> manualElementAddList = Arrays.asList("eber_ish_result", "rna_integrity");
	private static String geneticElement = "\'genetic_testing_result\'";
	private static String immunoElement = "\'immunophenotypic_analysis_tested\'";
	
	public Row() {}
	
	public Row(String tcgaBarcode) {
		this.tcgaBarcode = tcgaBarcode;
	}
	
	public String getTcgaBarcode() {
		return tcgaBarcode;
	}

	public void setTcgaBarcode(String tcgaBarcode) {
		this.tcgaBarcode = tcgaBarcode;
	}
	
	public void addCell(Cell cell, HeaderCell headerCell) {
		cellLookup.put(headerCell, cell);
	}
	
	/**
	 * This should ONLY be called after all of the XML is parsed and the <code>Table</code> object
	 * is completely built along with all discrepancies associated with display metadata are resolved. 
	 * 
	 * @param headerCell
	 * @return
	 * @throws DiscrepancyResolutionNotCompleteException 
	 */
	public Cell getCellAt(HeaderCell headerCell) throws DiscrepancyResolutionNotCompleteException {
		return cellLookup.get(headerCell);
	}
	
	public Cell removeCellAt(HeaderCell headerCell) {
		return cellLookup.remove(headerCell);
	}
	
    public void resolveDiscrepancies(DisplayMetadataCache cache) {
    	// TODO: implement.
    	resolved = true;
    }
    
	public boolean isResolved() {
		return resolved;
	}
   
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Row.class).createMarshaller();
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
	
	public StringBuilder toBiotabString(Header<HeaderCell> header, JobDTO jobDTO) throws DiscrepancyResolutionNotCompleteException {
		StringBuilder result = new StringBuilder();
		boolean start = true;
		if (!header.hasBarcode()) {
			start = false;
			result.append(tcgaBarcode != null ? tcgaBarcode : "");
		}
		for (HeaderCell headerCell : header) {
			if (start) {
				start = false;
				result.append(this.getCellAt(headerCell) != null ? this.getCellAt(headerCell).toBiotabString(jobDTO) : "");
			} else {
				result.append('\t').append(this.getCellAt(headerCell) != null ? this.getCellAt(headerCell).toBiotabString(jobDTO) : "");
			}
		}
		result.append(System.getProperty("line.separator"));
		return result;
	}
	
	public static Row<Cell> createInstance(JobDTO jobDTO, BioTabFileDTO bioTabFileDTO, XQueryUtil xQueryUtil, 
			String tcgaBarcode, XmlObject clinincalRoot, Header<HeaderCell> header, String xpathToContainerWithPositionalPredicate, Boolean replaceXPath) {
		Row<Cell> result = new Row<Cell>(tcgaBarcode);
		
		String xpath = null;
		String xPathManualAdd = null;
		XmlObject[] children = null;
		XmlObject[] childrenManualAdd = null;
		try 
		{
		
		if ( (bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("treatment") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("ablation") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("drug") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("prescreen") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("genetic_testing_results") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("genetic_abnormality_testing_results") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("patient") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("radiation") && header.getCountOfElement("bcr_patient_uuid") == 0) ||
				 bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("followup") > -1 && header.getCountOfElement("bcr_patient_uuid") == 0 ||
				 bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("nte") > -1 && header.getCountOfElement("bcr_patient_uuid") == 0 ||
				 bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("omf") && header.getCountOfElement("bcr_patient_uuid") == 0 ) {
				
			//Added for BCRI-2832 - Add subject UUID as left-most column in biotabs
			HeaderCell hc = new HeaderCell();
			String studyCode = bioTabFileDTO.getTable().getDiseaseCode().toLowerCase();
			hc.setCde("");
			hc.setDisplayOrder(-120);
			hc.setElementName("bcr_patient_uuid");
			hc.setPreferredName("bcr_patient_uuid");
			hc.setRelativeXpath("/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/shared/2.7']");
			hc.setRepeating(false);
			hc.setSortByDisplayOrder(false);
			if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.CLINICAL)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + studyCode + "/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + studyCode + "/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.OMF)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/omf/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/omf/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.SSF)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/ssf/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/ssf/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.PPS)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/prescreen/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/prescreen/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else {
				throw new IllegalArgumentException("Unknown XmlFileType: " + jobDTO.getXmlFileType());
			}
			header.addHeaderCellAt(0, hc);
			//End
			hc = new HeaderCell();
			studyCode = bioTabFileDTO.getTable().getDiseaseCode().toLowerCase();
			hc.setCde("2003301");
			hc.setDisplayOrder(-115);
			hc.setElementName("bcr_patient_barcode");
			hc.setPreferredName("bcr_patient_barcode");
			hc.setRelativeXpath("/*[local-name()='bcr_patient_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			hc.setRepeating(false);
			hc.setSortByDisplayOrder(true);
			if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.CLINICAL)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + studyCode + "/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + studyCode + "/2.7']/*[local-name()='bcr_patient_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.OMF)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/omf/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/omf/2.7']/*[local-name()='bcr_patient_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.SSF)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/ssf/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/ssf/2.7']/*[local-name()='bcr_patient_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.PPS)) {
				hc.setXpath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/prescreen/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/prescreen/2.7']/*[local-name()='bcr_patient_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
			} else {
				throw new IllegalArgumentException("Unknown XmlFileType: " + jobDTO.getXmlFileType());
			}
			header.addHeaderCellAt(1, hc);

			if (bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("followup-nte") > -1) {
				hc = new HeaderCell();
				hc.setCde("");
				hc.setDisplayOrder(-100);
				hc.setElementName("bcr_followup_barcode");
				hc.setPreferredName("bcr_followup_barcode");
				hc.setRepeating(false);
				hc.setSortByDisplayOrder(false);
				hc.setRelativeXpath("/../../*[local-name()='bcr_followup_barcode' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/shared/2.7']");
				header.addHeaderCellAt(2, hc);
			}
		}
			
		for (HeaderCell headerCell : header) {
			String headerCellElement = headerCell.getElementName();
			String headerCellPrefName = headerCell.getPreferredName();
			if (bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("patient") || 
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("treatment") && 
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date"))
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("ablation") && 
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date"))
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("prescreen") &&
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date"))
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("genetic_testing_results") &&
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date"))
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("genetic_abnormality_testing_results") &&
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date"))
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("drug") &&
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date")) 
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("nte") > -1 &&
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date")) 
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("radiation") && 
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date")) 
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("followup") > -1 && 
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date")) 
					||
					(bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("omf") && 
							headerCellElement.equals("bcr_patient_barcode") || headerCellPrefName.equals("form_completion_date")) 		
				) {
				
				if (headerCellPrefName.equals("form_completion_date") && !bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("patient")) {
					xpath = headerCell.getXpath().replace("xpathToContainerWithPositionalPredicate", xpathToContainerWithPositionalPredicate);
				} else {
					xpath = headerCell.getXpath();
				}
				
			} else {  
				
				//xpath = headerCell.getXpath(bioTabFileDTO.getSectionToFileMapNode().getElementName(), index);
				//xpath = headerCell.getXpath(headerCell.getXpath(), index);
                if(headerCellElement.equals("bcr_followup_barcode") && bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("followup-nte")){
                    xpath = headerCell.getXpathWithPositionalPredicates(xpathToContainerWithPositionalPredicate);
                    //Not all XSDs nest the followup new tumor event section at the same depth.
                    //This xpath applies a boolean check to see if it finds an element. If it does not
                    //then it will only go up one level instead of two.
                    xpath = "if(boolean(" + xpath + ")) then " + xpath + " else " + xpath.replace("/../../*[local-name()='bcr_followup_barcode' ","/../*[local-name()='bcr_followup_barcode' ");
                } else {
				    xpath = headerCell.getXpathWithPositionalPredicates(xpathToContainerWithPositionalPredicate);
                }
                
                // the below will help to write "eber_genetic_testing_result" section data as part of "genetic_testing_result"(PPS) biotab section file
                if( jobDTO.getDiseaseCode().equalsIgnoreCase("DLBC") && xpath.contains( geneticElement ) )
                {
                	String xPathTemp = xpath;
                	xPathManualAdd = xPathTemp.replace(geneticElement, "\'eber_genetic_testing_result\'");
                	
                	if( xPathTemp.contains( immunoElement ) )
                		xPathManualAdd = xPathManualAdd.replace(immunoElement,"\'" + headerCellElement + "\'");

                }
            
			}

			children = clinincalRoot.selectPath(xQueryUtil.appendNamespaceDeclarationForXQuery(xpath));
			childrenManualAdd = clinincalRoot.selectPath( xQueryUtil.appendNamespaceDeclarationForXQuery( xPathManualAdd ) );
			
			if (children.length < 1) {
				result.addCell(Cell.createInstance(null, ""), headerCell);
				//Added for BCRI-2832 - Add subject UUID as left-most column in biotabs
				if(headerCellElement.equals("bcr_patient_uuid")) {
					if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.OMF)) {
						Cell cell = new Cell();
						cell.setProcurmentStatus("Completed");
						XmlObject[] uuidObj = clinincalRoot.selectPath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/omf/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/omf/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
						cell.setValue(getProperCellValue(uuidObj[0]));
						result.addCell(cell, headerCell);
					} else if (jobDTO.getXmlFileType().equals(BaseBiotaberApp.XmlFileType.PPS)) {
						Cell cell = new Cell();
						cell.setProcurmentStatus("Completed");
						XmlObject[] uuidObj = clinincalRoot.selectPath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/prescreen/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/prescreen/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
						cell.setValue(getProperCellValue(uuidObj[0]));
						result.addCell(cell, headerCell);
					} else {
						Cell cell = new Cell();
						cell.setProcurmentStatus("Completed");
						XmlObject[] uuidObj = clinincalRoot.selectPath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + bioTabFileDTO.getTable().getDiseaseCode().toLowerCase() + "/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + bioTabFileDTO.getTable().getDiseaseCode().toLowerCase() + "/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
						cell.setValue(getProperCellValue(uuidObj[0]));
						result.addCell(cell, headerCell);
					}
					//End
				}

			} else {
				
				boolean start = true;
				String calculatedValue = "";
				
				
				if( bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("treatment") ||
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("ablation") ||
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("omf") ||
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("drug") ||
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("patient") ||
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("nte") > -1|| 
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("radiation") ||
						bioTabFileDTO.getSectionToFileMapNode().getSectionId().indexOf("followup") > -1 ) {

					//Added for BCRI-2832 - Add subject UUID as left-most column in biotabs
					if(headerCellElement.equals("bcr_patient_uuid")) {
						Cell cell = new Cell();
						cell.setProcurmentStatus("Completed");
						XmlObject[] uuidObj = clinincalRoot.selectPath("//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + bioTabFileDTO.getTable().getDiseaseCode().toLowerCase() + "/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + bioTabFileDTO.getTable().getDiseaseCode().toLowerCase() + "/2.7']/*[local-name()='bcr_patient_uuid' and namespace-uri()='http://tcga.nci/bcr/xml/shared/2.7']");
						cell.setValue(getProperCellValue(uuidObj[0]));
						result.addCell(cell, headerCell);
						continue;
					}
					//End
					if(headerCellElement.equals("bcr_patient_barcode")) {
						Cell cell = new Cell();
						cell.setProcurmentStatus("Completed");
						cell.setValue(tcgaBarcode);
						result.addCell(cell, headerCell);
						continue;
					}
				}
				
				if (children.length == 1 ) {
								
					calculatedValue = getProperCellValue(children[0]);
					
					// manual call to create a new row instance and add to biotab file, if it matches the below criteria
					if( !replaceXPath && xPathManualAdd!= null && xpath.contains( geneticElement ) && xpath.contains("\'bcr_slide_uuid\'") )
					{						
						if( childrenManualAdd.length == 1 ) {
							bioTabFileDTO.getTable().addRow( Row.createInstance(jobDTO, bioTabFileDTO, xQueryUtil, tcgaBarcode, clinincalRoot, header, xpathToContainerWithPositionalPredicate, true) );
						}
					}
					
					// add appropriate values for the "genetic_testing_result" section biotab
					if( replaceXPath && childrenManualAdd.length == 1 && xpath.contains( geneticElement ) )
					{
						calculatedValue = getProperCellValue(childrenManualAdd[0]);
						
						if( xpath.contains( immunoElement ) && manualElementAddList.contains( headerCellElement ) ) 
							result.addCell(Cell.createInstance(children[0], calculatedValue), headerCell);
						else
							result.addCell(Cell.createInstance(children[0], calculatedValue), headerCell);
						
						continue;
					}
					else if ( replaceXPath && headerCellElement.contains("immunophenotypic_analysis_") )
					{
						result.addCell(Cell.createInstance(children[0], "[Not Applicable]"), headerCell);
						continue;
					}
					else if( xpath.contains( immunoElement ) && manualElementAddList.contains( headerCellElement ) )
					{
						result.addCell(Cell.createInstance(children[0], "[Not Applicable]"), headerCell);
						continue;
					}
					
					
				} else {
					
					// add appropriate values for the "genetic_testing_result" section biotab
					if( replaceXPath && xpath.contains("\'tma_coordinate\'") )
					{
						result.addCell(Cell.createInstance(children[0], "[Not Applicable]"), headerCell);
						continue;
					}
					
					for (XmlObject child : children) 
					{
						if (start) {
							String cellPartValue = getProperCellValue(child);
							calculatedValue += cellPartValue;
							start = false;
						} else {
							String cellPartValue = getProperCellValue(child);
							calculatedValue += ("|" + cellPartValue);
						}
					
					}
				}
				result.addCell(Cell.createInstance(children[0], calculatedValue), headerCell);
			}
		}
		
		} catch(Exception e) {
			log.error("Exception for XPath: " + xpath);
			throw e;
		}
		return result;
	}

    private static String getProperCellValue(XmlObject xmlObject){
        String cellPartValue = XMLBeansUtils.extractElementTextAsString(xmlObject);
        String procurementStatus = XMLBeansUtils.getProcurmentStatus(xmlObject).equals("")
                ? "Not Available"
                : XMLBeansUtils.getProcurmentStatus(xmlObject);
        //Use procurement status in place of "invalid" cell values
        if (cellPartValue == null || cellPartValue.trim().equals("") || cellPartValue.equals("--")) {
            cellPartValue = '[' + procurementStatus + ']';
        }
        return cellPartValue;
    }

	public boolean isRowEmpty(Header<HeaderCell> header) throws DiscrepancyResolutionNotCompleteException {
		boolean result = true;
		for (HeaderCell headerCell : header) {
			// We want to make sure that all elements other than the barcode and the uuid have a value of [Not Available]
			// The barcode and uuid will or at least should always have a value, but these are not taken into account 
			// when determining if a row is blank. 
			//
			// This is used for the NTE biotabs, because there are a number of NTE rows in OC that have no answers. This 
			// propagates into the XML as XML blocks with no values. 
			if (!headerCell.getElementName().contains("barcode") && !headerCell.getElementName().contains("uuid")) {
				if (!getCellAt(headerCell).getProcurmentStatus().equalsIgnoreCase("Not Available") && 
						!getCellAt(headerCell).getProcurmentStatus().equalsIgnoreCase("Not Applicable")) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

}
