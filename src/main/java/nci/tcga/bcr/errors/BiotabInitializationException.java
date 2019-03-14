package nci.tcga.bcr.errors;

/**
 * Thrown if there are any issues intitializing this application. 
 * 
 * @author John Deardurff 
 */
public class BiotabInitializationException extends Exception {
	
	private static final long serialVersionUID = 1L;

    public BiotabInitializationException(String message) {
        super(message);
    }
}
