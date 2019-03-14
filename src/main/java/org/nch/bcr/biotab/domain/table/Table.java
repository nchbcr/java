package org.nch.bcr.biotab.domain.table;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.nch.bcr.biotab.app.JobDTO;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataCache;
import org.nch.bcr.biotab.util.BarcodeComparator;

/**
 * This is a DTO for all data that goes into a row of a tab-delimited file, which consists 
 * of at least a list of a list of <Row> objects and a <Header> object. 
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table<T> implements Iterable<Row<Cell>>, DiscrepancyResolvable<DisplayMetadataCache>  {
	
	@XmlAttribute
	private String diseaseCode = "";
	
	@XmlElement(name = "header")
	Header<HeaderCell> header;
	
	@XmlElementWrapper(name="rows")
	@XmlElement(name = "row")
	protected ArrayList<Row<Cell>> rows = new ArrayList<Row<Cell>>();
	
	@XmlAttribute
	private boolean resolved = false;
	
	
	public Table() {}
	
	public Table(String diseaseCode, Header<HeaderCell> header) {
		this.diseaseCode = diseaseCode;
		this.header = header;
	}
	
	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public Header<HeaderCell> getHeader() {
		return header;
	}

	public void setHeader(Header<HeaderCell> header) {
		this.header = header;
	}

	public Row<Cell> getRowAt(int i) {
		return rows.get(i);
	}
	
	public void addRow(Row<Cell> row) {
		rows.add(row);
	}
	
	public boolean isEmpty() {
		return rows.size()<1 ? true : false;
	}
	
	@Override
    public Iterator<Row<Cell>> iterator() {
        Iterator<Row<Cell>> it = new Iterator<Row<Cell>>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < rows.size() && rows.get(currentIndex) != null;
            }

            @Override
            public Row<Cell> next() {
                return rows.get(currentIndex);
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
            }
        };
        return it;
    }
    
    public void resolveDiscrepancies(DisplayMetadataCache cache) {
    	// TODO: implement.
    	
    	resolved = true;
    }
    
	public boolean isResolved() {
		return resolved;
	}
	
	public boolean hasBarcode() {
		return header.hasBarcode();
	}
	
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(Table.class).createMarshaller();
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
	
	public StringBuilder toBiotabString(JobDTO jobDTO) throws DiscrepancyResolutionNotCompleteException {
		StringBuilder result = new StringBuilder();

		//Added for BCRI-2832 - Add subject UUID as left-most column in biotabs
		Collections.sort(rows, new BarcodeComparator());
		//End
		result.append(header.toBiotabString(jobDTO));
		for (Row<Cell> row : rows) {
			result.append(row.toBiotabString(header, jobDTO));
			//result.append(System.getProperty("line.separator"));
		}

		return result;
	}
	
	/**
	 * Removes all <code>HeaderCell</code> from the header and all corresponding <code>Cell</code> objects from the rows
	 * for all columns that have not data. 
	 * @throws DiscrepancyResolutionNotCompleteException 
	 */
	public void purgeEmptyColumns() throws DiscrepancyResolutionNotCompleteException {
		ArrayList<HeaderCell> emptyColumns = new ArrayList<HeaderCell>(5);
		// First get all of the headerCell objects that correspond to empty columns. 
		for (HeaderCell headerCell : header) {
			if (isColumnEmpty(headerCell)) {
				emptyColumns.add(headerCell);
			}
		}
		// Now for each headerCell object that corresponds to an empty column, go through 
		// all of the row and remove cell corresponding to the current headerCell.
		for (HeaderCell headerCell : emptyColumns) {
			for (Row<Cell> row : rows) {
				Cell removedCell = row.removeCellAt(headerCell);
				// TODO: Log what was removed. 
				if (removedCell == null) {
					// TODO: This is an error condition that needs to be handled!
				}
			}
		}
	}
	
	public boolean isColumnEmpty(HeaderCell headerCell) throws DiscrepancyResolutionNotCompleteException {
		boolean result = true;
		for (Row<Cell> row : rows) {
			if (!row.getCellAt(headerCell).getValue().equals("")) {
				result = false;
				break;
			}
		}
		return result;
	}

}
