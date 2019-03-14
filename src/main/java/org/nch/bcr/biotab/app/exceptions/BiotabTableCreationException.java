package org.nch.bcr.biotab.app.exceptions;

/**
 * Thrown if there are any issues creating ad populating a <code>Table</code> object. 
 * 
 * @author John Deardurff 
 */
public class BiotabTableCreationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	protected String category = "A general wrapper exception that is thrown whenever a biotab Table, TableRow, or TableCell object could not be created.";

    public BiotabTableCreationException(String message) {
        super(message);
    }

    public BiotabTableCreationException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }
    
   
}