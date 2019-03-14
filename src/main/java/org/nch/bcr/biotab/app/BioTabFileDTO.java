package org.nch.bcr.biotab.app;

import org.nch.bcr.biotab.config.SectionToFileMapNode;
import org.nch.bcr.biotab.domain.table.Cell;
import org.nch.bcr.biotab.domain.table.Header;
import org.nch.bcr.biotab.domain.table.HeaderCell;
import org.nch.bcr.biotab.domain.table.Row;
import org.nch.bcr.biotab.domain.table.Table;

/**
 * The core working data bean. It stores the working <code>Table</code> the <code>Header</code> associated with the <code>Table</code>, and 
 * the <code>SectionToFileMapNode</code> that instructed how to build the <code>Header</code>.
 *
 * @author John Deardurff
 *
 */
public class BioTabFileDTO {
	
	protected Header<HeaderCell> header = null;
	protected Table<Row<Cell>> table = null;
	protected SectionToFileMapNode sectionToFileMapNode = null;
	
	public BioTabFileDTO(SectionToFileMapNode sectionToFileMapNode) {
		this.sectionToFileMapNode = sectionToFileMapNode;
	}

	public Header<HeaderCell> getHeader() {
		return header;
	}

	public void setHeader(Header<HeaderCell> header) {
		this.header = header;
	}
	
	public Table<Row<Cell>> getTable() {
		return table;
	}

	public void setTable(Table<Row<Cell>> table) {
		this.table = table;
	}

	public SectionToFileMapNode getSectionToFileMapNode() {
		return sectionToFileMapNode;
	}

	public void setSectionToFileMapNode(SectionToFileMapNode sectionToFileMapNode) {
		this.sectionToFileMapNode = sectionToFileMapNode;
	}
}
