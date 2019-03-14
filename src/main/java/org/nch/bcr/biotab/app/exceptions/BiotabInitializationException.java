package org.nch.bcr.biotab.app.exceptions;

/**
 * Thrown if there are any issues intitializing this application. Some such issues are populating the 
 * internal cache of tblClinicalCrfDataDictionary, parsing sectionToFileMaps.groovy, etc. 
 * 
 * @author John Deardurff 
 */
public class BiotabInitializationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	protected String category = "A general wrapper exception that is thrown whenever the biotab app could not be initialized.";

    public BiotabInitializationException(String message) {
        super(message);
    }

    public BiotabInitializationException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }
}
