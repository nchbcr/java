/**
 * 
 */
package nci.tcga.bcr.beans;

import java.util.Date;

/**
 * @author axk058
 *
 */
public class TextToDate {

	private Date date = null;
	private DateObject obj = null;
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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
