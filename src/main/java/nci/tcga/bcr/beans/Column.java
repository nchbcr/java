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
public class Column implements Validatable, Iterable<Cell> {

	private List<Cell> column;
	
	public Column(List<Cell> cells) {
		this.column = new ArrayList<Cell>();
		for(Cell cell : cells) {
			this.column.add(cell);
		}
	}
	
	public Column() {
		this.column = new ArrayList<Cell>();
	}
	
	public List<Cell> getColumn() {
		return this.column;
	}
	
	public void setColumn(List<Cell> column) {
		this.column = column;
	}
	
	public Cell get(int index) {
		return column.get(index);
	}
	
	public Cell setCell(Cell cell, int index) {
		return column.set(index, cell);
	}
	
	public boolean add(Cell cell) {
		return column.add(cell);
	}
	
	public void addCellByIndex(Cell cell, int index) {
		column.add(index, cell);
	}
	
	public int numberOfCells() {
		return column.size();
	}
	
	public Cell removeCell(int index) {
		return column.remove(index);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column cells = (Column) o;

        if (column != null ? !column.equals(cells.column) : cells.column != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return column != null ? column.hashCode() : 0;
    }

    public Iterator<Cell> iterator() {
		Iterator<Cell> iter = column.iterator();
		return iter;
	}

	@Override
	public String toString() {
		return column.get(0).getElementName();
	}

	
	private boolean cellsAreValid() {
		for(Cell c : column) {
			if(c.isValid()) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isValid() {
		return column !=null
				&& !column.isEmpty()
				&& cellsAreValid();
	}
}
