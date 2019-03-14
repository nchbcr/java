package org.nch.bcr.biotab.versions;

/**
 * Exception thrown when there are errors reading/parsing the XSD version control file. 
 * 
 * @author John Deardurff
 *
 */
public class XsdVersionFileException extends Exception {
	
	private static final long serialVersionUID = -4448986166383452017L;

	protected String category = "The xsd version control file could not be parsed.";

    public XsdVersionFileException(String message) {
        super(message);
    }

    public XsdVersionFileException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

}
