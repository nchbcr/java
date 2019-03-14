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

    
public class Row implements Validatable, Iterable<Cell> {
	private List<Cell> row;
	
	public Row() {
		row = new ArrayList<Cell>();
	}
	
	public Cell getCellAtIndex(int index) {
		return row.get(index);
	}
	
	public int size() {
		return row.size();
	}
	
	public boolean add(Cell cell) {
		return row.add(cell);
	}

	public Iterator<Cell> iterator() {
		Iterator<Cell> iter = row.iterator();
		return iter;
	}
	
	public Row addCellToIndex(Cell cell, int index) {
		Row row = new Row();
		for(int i = 0; i < index; i++) {
			row.add(new Cell());
		}
		row.add(cell);
		return row;
	}
	
	public boolean isRowNull() {
		for(Cell cell : row) {
			if(cell != null) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isValid() {
		return row !=null && !row.isEmpty();
	}
}

