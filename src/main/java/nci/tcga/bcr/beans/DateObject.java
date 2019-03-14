/**
 * 
 */
package nci.tcga.bcr.beans;

/**
 * @author axk058
 *
 */
public class DateObject {

	private String procurement;
	private boolean isError;
	private String warningMessage;
	private String fatalMessage;
	
	/**
	 * @return the procurement
	 */
	public String getProcurement() {
		return procurement;
	}

	/**
	 * @param procurement the procurement to set
	 */
	public void setProcurement(String procurement) {
		this.procurement = procurement;
	}

	/**
	 * @return the warningMessage
	 */
	public String getWarningMessage() {
		return warningMessage;
	}

	/**
	 * @param warningMessage the warningMessage to set
	 */
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	/**
	 * @return the fatalMessage
	 */
	public String getFatalMessage() {
		return fatalMessage;
	}

	/**
	 * @param fatalMessage the fatalMessage to set
	 */
	public void setFatalMessage(String fatalMessage) {
		this.fatalMessage = fatalMessage;
	}

	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * @param isError the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}
}
