package org.nch.bcr.biotab.domain.table;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.xmlbeans.SchemaParticle;
import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.clinical.app.ClinicalBiotaberApp;
import org.nch.bcr.biotab.config.SectionToFileMapNode;
import org.nch.bcr.biotab.config.XPathPartsList;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataItem;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataKey;
import org.nch.bcr.biotab.util.XMLBeansXSDUtilsBasic;

/**
 * This is a DTO for all data that goes into a header cell of a tab-delimited file. 
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "headerCell")
@XmlAccessorType(XmlAccessType.FIELD)
public class HeaderCell implements Comparable<HeaderCell> {
	
	/**
	 *  This is the xpath without positional predicates and it valid, but returns ALL occurrences regardless 
	 *  of the section instance. So, for nested sections like NTEs in a followup it would not get the NTE cell(s) 
	 *  for a specific followup.
	 *  <p>
	 *  This can be used for the patient section since there is only one patient section. 
	 *  </p>
	 */
	@XmlElement(name = "xpath") 
	private String xpath = "";
	
	/**
	 * This is the xpath relative to the container xpath as specified in the corresponding  
	 * <code>SectionToFileMapNode.containerXPath</code>. Again this is without positional predicates.
	 * <p>
	 * This is used to form a fully qualified xpath from root by the following:
	 * 		XPATH_TO_CONTAINER_with_POSITIONAL_PREDICATES + relativeXpath + 
	 * </p>
	 * 
	 * @see this.getXpathWithPositionalPredicates(String xpathToContainerWithPositionalPredicate)
	 */
	@XmlElement(name = "relativeXPath")
	private String relativeXpath = "";

	@XmlAttribute
	private String cde = "";
	
	@XmlAttribute
	private String preferredName = "";
	
	@XmlAttribute
	private Integer displayOrder = 999;
	
	@XmlTransient
	private SchemaParticle schemaParticle = null;
	
	@XmlAttribute
	private boolean isRepeating = false;
	
	@XmlAttribute
	private String elementName = "";
	
	@XmlAttribute
	private boolean sortByDisplayOrder = false;
	
	public HeaderCell() {}

	public HeaderCell(String xpath, String cde, String preferredName, Integer displayOrder, SchemaParticle schemaParticle) {
		super();
		this.xpath = xpath;
		this.cde = cde;
		this.preferredName = preferredName;
		if (displayOrder != null) {
			this.displayOrder = displayOrder;
		}
		this.schemaParticle = schemaParticle;
	}
	
	public String getXpath(String qname, Integer index) {
		String result = getXpath();
		if (index != null) {
			result = result.replace(qname, qname + "[" + index.toString() + "]");
		}
		return result;
	}
	
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	public String getRelativeXpath() {
		return relativeXpath;
	}

	public void setRelativeXpath(String relativeXpath) {
		this.relativeXpath = relativeXpath;
	}
	
	public String getXpathWithPositionalPredicates(String xpathToContainerWithPositionalPredicate) {
		return xpathToContainerWithPositionalPredicate + this.getRelativeXpath();
	}

	public String getCde() {
		return cde;
	}

	public void setCde(String cde) {
		this.cde = cde;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		if (displayOrder != null) {
			this.displayOrder = displayOrder;
		}
	}
	
	public SchemaParticle getSchemaParticle() {
		return schemaParticle;
	}

	public boolean isRepeating() {
		return isRepeating;
	}

	public void setRepeating(boolean isRepeating) {
		this.isRepeating = isRepeating;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public boolean isSortByDisplayOrder() {
		return sortByDisplayOrder;
	}

	public void setSortByDisplayOrder(boolean sortByDisplayOrder) {
		this.sortByDisplayOrder = sortByDisplayOrder;
	}

	public static HeaderCell createInstance(JobDTO jobDTO, SectionToFileMapNode currentSection, SchemaParticle schemaParticle, String parentXPath, String elementName) {
		HeaderCell result = new HeaderCell();
		
			
		result.setSortByDisplayOrder(Boolean.valueOf(jobDTO.getProperty("clinical.biotab.sort.by.display.order")));
		result.setCde(XMLBeansXSDUtilsBasic.getCdeFromSchemaParticle(schemaParticle));
		result.setElementName(schemaParticle.getName().getLocalPart());
		result.setPreferredName(result.getElementName());
		result.setRepeating((schemaParticle.getMaxOccurs()==null || schemaParticle.getMaxOccurs().intValue()!=1) ? true : false);
		result.setXpath(XMLBeansXSDUtilsBasic.composeXPath(parentXPath, schemaParticle));
		
		// if elementName is not null then create a new manually added header
		if( elementName != null ) {
			String xPath = result.getXpath();
			xPath.replace( "\'immunophenotypic_analysis_tested\'", "\'"+elementName+"\'" );
			
			result.setXpath( xPath );
			result.setElementName( elementName );
			result.setPreferredName( elementName );
			
			if( elementName.equalsIgnoreCase("eber_ish_result") )
				result.setCde( "3536327" );
			else if( elementName.equalsIgnoreCase("rna_integrity") )
				result.setCde( "5438395" );
			
			result.setDisplayOrder( 15 );
		}
		
		// Set the relativeXpath so that we can located sections that repeat within repeating sections such as NTE sections 
		// within followup sections. This is part of the fix that lets us correctly biotab NTE sections that spread
		// accross multiple followup sections. 
		result.setRelativeXpath( result.getXpath().substring(currentSection.getContainerXPath().length()) );
		
		DisplayMetadataKey key = new DisplayMetadataKey(jobDTO.getDiseaseCode(), currentSection.getFormType(), result.getCde());
		DisplayMetadataItem displayMetadata = ClinicalBiotaberApp.displayMetadataCache.getDisplayMetadataItem(key);
		if (displayMetadata != null) {
			if (displayMetadata.getDisplayOrder() != null) {
				result.setDisplayOrder(displayMetadata.getDisplayOrder());
			}
			if (displayMetadata.getPreferredName() != null && !displayMetadata.getPreferredName().trim().equals("")) {
				result.setPreferredName(displayMetadata.getPreferredName());
			}
		}
		
		if (Boolean.valueOf(jobDTO.getProperty("clinical.biotab.sort.by.display.order"))) {
			//Added following if for BCRI-2832 - Add subject UUID as left-most column in biotabs
			if ( "bcr_patient_uuid".equals(result.getPreferredName()) ) {
				result.setDisplayOrder(-120);
			}  else if ("bcr_treatment_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-100);
			}  else if ("bcr_ablation_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-100);
			} else if ("days_to_ablation_performed".equals(result.getPreferredName())) {
				result.setDisplayOrder(34);
			} else if ("days_to_embolization_performed".equals(result.getPreferredName())) {
				result.setDisplayOrder(74);
			} else if ("bcr_radiation_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-100);
			} else if ("bcr_drug_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-100);
			} else if ("bcr_followup_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-100);
			} else if ("bcr_omf_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-100);
			} else if("bcr_patient_barcode".equals(result.getPreferredName())) {
				result.setDisplayOrder(-115);
			} else if (result.getPreferredName().indexOf("_uuid") > -1 && result.getPreferredName().indexOf("bcr_") > -1) {
				result.setDisplayOrder(-95);
			} else if (result.getPreferredName().equals("form_completion_date")) {
				result.setDisplayOrder(-80);
			}
		}
		return result;
	}
	
	@Override
	public int compareTo(HeaderCell o) {
		if (this.sortByDisplayOrder) {
			if (this.getDisplayOrder() < 999 && o.getDisplayOrder() < 999) {
				return this.getDisplayOrder().compareTo(o.getDisplayOrder());
			} else if (this.getDisplayOrder() >= 999 && o.getDisplayOrder() >= 999) {
				return this.getPreferredName().compareTo(o.getPreferredName());
			} else if (this.getDisplayOrder() < 999) {
				return -1;
			} else {
				return 1;
			}
		} else {
			return this.getPreferredName().compareTo(o.getPreferredName());
		}
	}
	
	
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(HeaderCell.class).createMarshaller();
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
