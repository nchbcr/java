package org.nch.bcr.biotab.domain.table;

/**
 * 
 * @author John Deardurff 
 */
public class DiscrepancyResolutionNotCompleteException extends Exception {
	
	private static final long serialVersionUID = 1L;

    public DiscrepancyResolutionNotCompleteException(String message) {
        super(message);
    }

    public DiscrepancyResolutionNotCompleteException(String message, Throwable t) {
        super(message, t);
    }

}