package nci.tcga.bcr.utilities;

import java.util.Comparator;

import nci.tcga.bcr.beans.Column;

public class CustomComparator implements Comparator<Column> {
	
	/**
	 * Compare the columns first by the display order value than by its natural order
	 */
	public int compare(Column column1, Column column2) {
		if(column1.get(0).getDisplayOrder() != null && column2.get(0).getDisplayOrder() != null) {
			if(Integer.parseInt(column1.get(0).getDisplayOrder()) < Integer.parseInt(column2.get(0).getDisplayOrder())) {
				return -1;
			}
			else if(Integer.parseInt(column1.get(0).getDisplayOrder()) == Integer.parseInt(column2.get(0).getDisplayOrder())) {
				return column1.get(0).getElementName().compareTo(column2.get(0).getElementName());
			}
			else {
				return 1;
			}
		}
		else if(column1.get(0).getDisplayOrder() != null && column2.get(0).getDisplayOrder() == null) {
			return -1;
		}
		else if(column1.get(0).getDisplayOrder() == null && column2.get(0).getDisplayOrder() != null) {
			return 1;
		}
		
		return column1.get(0).getElementName().compareTo(column2.get(0).getElementName());
	}
	

}
