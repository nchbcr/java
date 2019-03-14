package nci.tcga.bcr.errors;

/**
 * @author axk058
 *
 */
public class DateFatalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4163796260537401208L;

	public DateFatalException() {
		super();
	}
	
	public DateFatalException(String message) {
		super(message);
	}
}
