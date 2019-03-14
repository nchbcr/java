package org.nch.bcr.biotab.app.exceptions;

/**
 * Thown if there are issues creating and populating <code>Header</code>. Header biotab file has an instance of <code>Header</code>
 * 
 * @author John Deardurff 
 */
public class BiotabHeaderCreationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	protected String category = "A general wrapper exception that is thrown whenever a biotab header object could not be created.";

    public BiotabHeaderCreationException(String message) {
        super(message);
    }

    public BiotabHeaderCreationException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

}
