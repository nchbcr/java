package org.nch.bcr.biotab.domain.table;

import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.xmlbeans.XmlObject;
import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.util.XMLBeansUtils;

/**
 * This is a DTO for all data that goes into a cell of a tab-delimited file. 
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "cell")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cell implements DiscrepancyResolvable<HeaderCell> {
	
	@XmlAttribute
	private String value = "";
	
	@XmlAttribute
	private String procurmentStatus = "";
	
	
	// This is the resolved display metadata, which may be different than what is in the 
	// XML file. The primary reason for this discrepancy is that xml for all cases is 
	// generated for a study. For example, if a form goes out of signature then XML cannot be 
	// generated. The process is to parse all the XML into <code>cell</code> object, then 
	// from that create <code>HeaderCell</code> through a process of discrepancy resolution. 
	@XmlElement(name = "resolvedDisplayMetadata")
	private HeaderCell headerCell = null;
	
	@XmlAttribute
	private boolean resolved = false;
	
	public Cell() {}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getProcurmentStatus() {
		return procurmentStatus;
	}

	public void setProcurmentStatus(String procurmentStatus) {
		this.procurmentStatus = procurmentStatus;
	}

	public HeaderCell getHeaderCell() throws DiscrepancyResolutionNotCompleteException {
		if (resolved) {
			return headerCell;
		}
		throw new DiscrepancyResolutionNotCompleteException("Discrepancy resolution must be completed before a HeaderCell is available for a Cell object.");
	}
	
	public void resolveDiscrepancies(HeaderCell headerCell) {
		this.headerCell = headerCell;
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
			marshaller = JAXBContext.newInstance(Cell.class).createMarshaller();
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
	
	public String toBiotabString(JobDTO jobDTO) {
		// The default value for the cell of the biotab file is the value, however if the value 
		// is the EMPTY_STRING then we need to printout the procurment status. 
		String result = this.getValue();
		if (Boolean.valueOf(jobDTO.getProperty("clinical.biotab.set.procurment.status.as.cell.value"))) {
			if (getValue().equals("")) {
				result = '[' + this.getProcurmentStatus() + ']';
			} 
		}
		return result;
	}
	
	public static Cell createInstance(XmlObject xmlObject, String computedElementValue) {
		Cell result = new Cell();
		result.setValue(computedElementValue);
		
		// Since it is possible to NOT have an xmlObject (optional elements and such), we need to check for that. 
		if (xmlObject != null) {
			Map<String, String> attributes = XMLBeansUtils.extractAttributes(xmlObject);
			String _procurmentStatus = attributes.get("procurement_status");
			if (_procurmentStatus == null || _procurmentStatus.trim().equals("")) {
				_procurmentStatus = "";
			}
			result.setProcurmentStatus(_procurmentStatus);
		} else {
			// This is the case where the xmlObject is null, which implies that the cell value is also the empty string.
			// So, in this case it is safe to assume that the procurement status should be set to 'Not Available'.
			result.setProcurmentStatus("Not Available");
		}
		return result;
	}
}
