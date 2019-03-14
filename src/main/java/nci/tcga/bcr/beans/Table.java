/**
 * 
 */
package nci.tcga.bcr.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author axk058
 *
 */
public class Table implements Validatable,Iterable<Column> {

	private List<Column> table;
    private Cell lastInsertedCell = new Cell();
	
	public Table() {
		table = new ArrayList<Column>();
	}
	
	public Table(List<Column> columns) {
		this();
		
		for(Column column: columns) {
			table.add(column);
		} 
	}
	
	public void addColumn(Column column) {
		table.add(column);
	}
	
	public void addColumn(int index, Column column) {
		table.add(index, column);
	}
	
	public void removeColumn(Column column) {
		table.remove(column);
	}
	
	public void removeColumn(int index) {
		table.remove(index);
	}
	
	public void removeRow(int index) {
		for(Column column : table) {
			column.removeCell(index);
		}
	}
	
	public void addRow(Row row) {
		int numOfCols = numberOfColumns();
		int sizeOfRow = row.size();
		
		if(numOfCols > 0) {
			int maxCells = table.get(0).numberOfCells();
			
			for(int a = 1; a < numOfCols; a++) {
				if(table.get(a).numberOfCells() > maxCells) {
					maxCells = table.get(a).numberOfCells();
				}
			}
			
			for(int i = 0, j = 0; i < numOfCols && j < sizeOfRow; i++, j++) {
				for(int b = table.get(i).numberOfCells(); b < maxCells; b++) {
					table.get(i).add(new Cell());
				}
				
				table.get(i).addCellByIndex(row.getCellAtIndex(j), maxCells);
			}
		}
		else {
			for(int k = 0; k < sizeOfRow; k++) {
				Column column = new Column();
				column.add(row.getCellAtIndex(k));
				table.add(column);
			}
		}
	}
	
	public void addTable(Table tableAdd) {
		int numOfColumns = numberOfColumns();
		int addNumOfRows = tableAdd.numberOfRows();
		int addNumOfColumns = tableAdd.numberOfColumns();
		
		for(int i = 0; i < addNumOfColumns; i++) {
			for(int j = 0; j < numOfColumns; j++) {
				if(tableAdd.get(i).get(0).getElementName().equals(table.get(j).get(0).getElementName())) {
					for(int k = 0; k < addNumOfRows; k++) {
						table.get(j).add(tableAdd.get(i).get(k));
					}
				}
			}
		}
	}
	
	public Row getRowByIndex(int index) {
		Row row = new Row();
		int numOfCols = numberOfColumns();
		
		for(int i = 0; i < numOfCols; i++) {
			Column column = table.get(i);
			Cell cell = new Cell();
			
			try {
				cell = column.get(index);
			}
			catch(IndexOutOfBoundsException ioobe) {
				String elementName;
				String cde;
				
				if(i > 0) {
					elementName = column.get(0).getElementName();
					cde = column.get(0).getCde();
				}
				else {
					elementName = "-- UNKNOWN --";
					cde = "-- UNKNOWN --";
				}
				
				cell.setElementName(elementName);
				cell.setCde(cde);
			}
			
			row.add(cell);
		}
		
		return row;
	}
	
	public int numberOfColumns() {
		return table.size();
	}
	
	public int numberOfRows() {
		int max = 0;
		int temp = 0;
		for(int i = 0; i < numberOfColumns(); i++) {
			temp = table.get(i).numberOfCells();
			if(temp > max) {
				max = temp;
			}
		}
		
		return max;
	}
	
	public void insertCell(Cell cell) {
		if(numberOfRows() > 1) {
			for(int i = 0; i < numberOfColumns(); i++) {
				if(table.get(i).get(0).getElementName().equals(cell.getElementName())) {
					table.get(i).add(cell);
					break;
				}
			}
		}
		else {
			Column column = new Column();
			column.add(cell);
			table.add(column);
		}

        lastInsertedCell = cell;
	}
	
	public Iterator<Column> iterator() {
		Iterator<Column> iter = table.iterator();
		return iter;
	}
	
	public Column get(int index) {
		return table.get(index);
	}
	
	public List<Column> asColumns() {
		List<Column> columns = new ArrayList<Column>();
		
		for(Column column: table) {
			columns.add(column);
		}
		
		return columns;
	}

    /**
     * Returns column whose first cell value matches a given string.
     * In practice, this usually means finding a column by its header
     *
     * @param columnName    The string to search form
     * @return              The column matching the string. <code>null</code> if no match found.
     */
	public Column getColumnByName(String columnName){
		for(Column c : table){
			if(c.numberOfCells() >= 1){
				if(c.get(0).getElementName().equals(columnName)){
					return c;
				}
			}
		}
		
		return null;
	}

    /**
     * Returns a string that can easily be printed to the console for debugging.
     * This method should <b>not</b> be used for final product generation. Also,
     * be aware of the size of the table you are printing.
     *
     * @return the table in string format.
     */
	public String printTable() {
		StringBuilder sb = new StringBuilder();
		for(Column column : table) {
			for(Cell cell : column.getColumn()){
				sb.append(cell.getElementValue());
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

    public Cell getLastInsertedCell(){
        return lastInsertedCell;
    }
    
    private boolean columnsHaveEqualLength() {
    	for(Column c : table){
    		int numberOfCells = c.numberOfCells();
    		int firstColumnCount = table.get(0).numberOfCells();
    		if(numberOfCells == firstColumnCount){
    			continue;
    		} else {
    			return false;
    		}
    	}
    	return true;
    }
    
    private boolean columnsAreValid() {
    	for(Column c : table){
    		if(c.isValid()){
    			continue;
    		} else {
    			return false;
    		}
    	}
    	return true;
    }

    
	@Override
	public boolean isValid() {
		return table !=null 
				&& !table.isEmpty()
				&& columnsHaveEqualLength()
				&& columnsAreValid();
	}

	
}

