/**
 * 
 */
package nci.tcga.bcr.beans;

/**
 * @author axk058
 *
 */
public class Cell implements Validatable {

	private String cde;
	private String preferredName;
	private String displayOrder;
	private String procurementStatus;
	private boolean isRestricted;
	private String elementName;
	private String elementValue;
	private String schemaVersion;

    public Cell(){};

    public Cell(Cell c){
        this.cde = c.cde;
        this.preferredName = c.preferredName;
        this.displayOrder = c.displayOrder;
        this.procurementStatus = c.procurementStatus;
        this.isRestricted = c.isRestricted;
        this.elementName = c.elementName;
        this.elementValue = c.elementValue;
        this.schemaVersion = c.schemaVersion;
    }
	
	/**
	 * @return the cde
	 */
	public String getCde() {
		return cde;
	}
	
	/**
	 * @param cde the cde to set
	 */
	public void setCde(String cde) {
		if(cde == null || cde.trim().equals("")) {
			this.cde = "";
		}
		else {
			this.cde = "CDE_ID:" + cde;
		}
	}
	
	/**
	 * @return the preferredName
	 */
	public String getPreferredName() {
		return preferredName;
	}
	
	/**
	 * @param preferredName the preferredName to set
	 */
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
	
	/**
	 * @return the displayOrder
	 */
	public String getDisplayOrder() {
		return displayOrder;
	}
	
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	/**
	 * @return the procurementStatus
	 */
	public String getProcurementStatus() {
		return procurementStatus;
	}
	
	/**
	 * @param procurementStatus the procurementStatus to set
	 */
	public void setProcurementStatus(String procurementStatus) {
		this.procurementStatus = procurementStatus;
	}
	
	/**
	 * @return the isRestricted
	 */
	public boolean getIsRestricted() {
		return isRestricted;
	}
	/**
	 * @param isRestricted the isRestricted to set
	 */
	public void setIsRestricted(boolean isRestricted) {
		this.isRestricted = isRestricted;
	}
	
	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}
	
	/**
	 * @param elementName the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	
	/**
	 * @return the elementValue
	 */
	public String getElementValue() {
		return elementValue;
	}
	
	/**
	 * @param elementValue the elementValue to set
	 */
	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

        @Override
        public boolean isValid(){
            boolean isValid = true;

            //Logic here to set isValid

            if (preferredName==null){
                isValid=false;
            }

            if (displayOrder==null){
                isValid=false;
            }

            if (procurementStatus==null){
                isValid=false;
            }

            if (elementName==null){
                isValid=false;
            }

            if (elementValue==null){
                isValid=false;
            }

            if (schemaVersion==null){
                isValid=false;
            }

            return isValid;
        }


	public String toString() {
		String value = getElementValue();
		String cde = getCde();
		String displayOrder = getDisplayOrder();
		
		if(value == null || value.equals("")) {
			value = null;
		}
		
		if(cde == null || cde.equals("")) {
			cde = null;
		}
		
		if(displayOrder == null || displayOrder.equals("")) {
			displayOrder = null;
		}
		
		return getElementName() + "\t" + value + "\t" + cde + "\t" + displayOrder + "\n";
	}
	
	public boolean equals(Object obj) {
		if(obj != null) {
			if(obj instanceof Cell) {
				if(this.getElementName().equals(((Cell) obj).getElementName())) {
					if(this.getElementValue() != null) { 
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public int hashCode() {
		return elementName.hashCode() * 17;
	}

	/**
	 * 
	 * @return
	 */
	public String getSchemaVersion() {
		return schemaVersion;
	}

	/**
	 * 
	 * @param schemaVersion
	 */
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}
	
	
}
