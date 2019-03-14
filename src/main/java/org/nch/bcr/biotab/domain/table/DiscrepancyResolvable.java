package org.nch.bcr.biotab.domain.table;

/**
 * 
 * @author John Deardurff 
 */
public interface DiscrepancyResolvable <T> {

	public void resolveDiscrepancies(T t);
	public boolean isResolved(); 
	
}
