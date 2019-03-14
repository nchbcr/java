/**
 * 
 */
package nci.tcga.bcr.beans;

/**
 * @author axk058
 *
 */
public class ValidDate {

	private boolean isValid;
	private DateObject obj;

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the obj
	 */
	public DateObject getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(DateObject obj) {
		this.obj = obj;
	}
	
	
}
