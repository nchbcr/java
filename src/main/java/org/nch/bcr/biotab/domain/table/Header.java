package org.nch.bcr.biotab.domain.table;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.nch.bcr.biotab.app.JobDTO;

/**
 * This is a DTO for all data that goes into a header of a tab-delimited file, 
 * which consists of at least a list of <HeaderCell> objects.  
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "header")
@XmlAccessorType(XmlAccessType.FIELD)
public class Header<T> implements  Iterable<HeaderCell> {
	
	@XmlElementWrapper(name="headerCells")
	@XmlElement(name = "headerCell")
	protected ArrayList<HeaderCell> cells = new ArrayList<HeaderCell>();
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public Header() {}
	
	public HeaderCell getHeaderCellAt(int i) {
		return cells.get(i);
	}
	
	public void addHeaderCellAt(int i, HeaderCell headerCell) {
		this.cells.add(i, headerCell);
	}
	
	public void addHeaderCell(HeaderCell headerCell) {
		cells.add(headerCell);
	}
	
	public void addAllHeaderCells(Collection<HeaderCell> headerCells) {
		cells.addAll(headerCells);
	}
	
	@Override
    public Iterator<HeaderCell> iterator() {
        Iterator<HeaderCell> it = new Iterator<HeaderCell>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < cells.size() && cells.get(currentIndex) != null;
            }

            @Override
            public HeaderCell next() {
            	currentIndex++;
                return cells.get(currentIndex-1);
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
            }
        };
        return it;
    }
    
	public void sort() {
		Collections.sort(cells);
	}
	
	public boolean hasBarcode() {
		boolean result = false;
		for (HeaderCell cell : cells) {
			if (cell.getElementName() != null && cell.getElementName().contains("barcode")) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	@Override
    public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Header.class).createMarshaller();
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
    
    public StringBuilder toBiotabString(JobDTO jobDTO) {
    	StringBuilder result = new StringBuilder();
    	
    	// Get the top row, which is the preferred name. 
    	boolean start = true;
    	if (!this.hasBarcode()) {
			start = false;
			result.append("bcr_patient_barcode");
		}
    	for (HeaderCell headerCell : this) {
    		if (start) {
    			start = false;
    			result.append(getCellValue(jobDTO, headerCell));
    		} else {
    			result.append('\t').append(getCellValue(jobDTO, headerCell));
    		}
    	}
    	result.append(LINE_SEPARATOR);

        //Get the second row of the header, which is the xml element name
        start = true;
        if (!this.hasBarcode()) {
            start = false;
            result.append("bcr_patient_barcode");
        }
        for (HeaderCell headerCell : this) {
            if(start) {
                start = false;
                result.append(headerCell.getElementName());
            } else {
                result.append('\t').append(headerCell.getElementName());
            }
        }
        result.append(LINE_SEPARATOR);
    	
    	// Get the third row of the header, which is the cde.
    	start = true;
    	if (!this.hasBarcode()) {
			start = false;
			result.append("CDE_ID:2003301");
		}
    	for (HeaderCell headerCell : this) {
    		if(start) {
    			start = false;
    			result.append("CDE_ID:").append(headerCell.getCde());
    		} else {
    			result.append('\t').append("CDE_ID:").append(headerCell.getCde());
    		}
    	}
    	result.append(LINE_SEPARATOR);
    	return result;
    }
    
    public String getCellValue(JobDTO jobDTO, HeaderCell headerCell) {
    	String result = null;
    	if (Boolean.valueOf(jobDTO.getProperty("clinical.biotab.append.display.order.to.header"))) {
    		result = headerCell.getPreferredName() + ":" + headerCell.getDisplayOrder();
    	} else {
    		result = headerCell.getPreferredName();
    	}
    	
    	return result;
    }
    
	public int getCountOfElement(String elementName) {
		int counter = 0;
		
		for(HeaderCell hc : this.cells) {
			if(hc.getElementName().equalsIgnoreCase(elementName)) {
				counter++;
			}
		}
		
		return counter;
	}

}
